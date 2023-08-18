package com.henriquechaves.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {
    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository repository){
        this.cashCardRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard> findById(@PathVariable Long id, Principal principal){

        Optional<CashCard> cashCardOptional = Optional.ofNullable(
                cashCardRepository.findByIdAndOwner(
                        id,
                        principal.getName()
                )
        );
        return cashCardOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity createCashCard(@RequestBody CashCard newCashCard, UriComponentsBuilder ucb, Principal principal){
        CashCard savedCashCard = cashCardRepository.save(new CashCard(null, newCashCard.amount(), principal.getName()));
        URI locationOfNewCashCard = ucb.path("api/v1/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal){
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));

        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> updateCashCard(@PathVariable Long id, @RequestBody CashCard data, Principal principal) {
        Optional<CashCard> cashCard = Optional.ofNullable(cashCardRepository.findByIdAndOwner(id, principal.getName()));
        if (cashCard.isPresent()) {
            CashCard cashCardUpdated = new CashCard(cashCard.get().id(), data.amount(), principal.getName());
            cashCardRepository.save(cashCardUpdated);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal){
        if(cashCardRepository.existsByIdAndOwner(id, principal.getName())){
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
