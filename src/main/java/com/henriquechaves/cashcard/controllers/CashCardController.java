package com.henriquechaves.cashcard.controllers;

import com.henriquechaves.cashcard.repositories.CashCardRepository;
import com.henriquechaves.cashcard.entities.CashCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CashCardRepository cashCardRepository;

    @Autowired
    public CashCardController(CashCardRepository repository){
        this.cashCardRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCardEntity> findById(@PathVariable Long id, Principal principal){

        Optional<CashCardEntity> cashCardOptional = Optional.ofNullable(
                cashCardRepository.findByIdAndOwner(
                        id,
                        principal.getName()
                )
        );
        return cashCardOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCardEntity newCashCard, UriComponentsBuilder ucb, Principal principal){
        CashCardEntity savedCashCard = cashCardRepository.save(new CashCardEntity(null, newCashCard.getAmount(), principal.getName()));
        URI locationOfNewCashCard = ucb.path("api/v1/cashcards/{id}").buildAndExpand(savedCashCard.getId()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCardEntity>> findAll(Pageable pageable, Principal principal){
        Page<CashCardEntity> page = cashCardRepository.findByOwner(principal.getName(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));

        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> updateCashCard(@PathVariable Long id, @RequestBody CashCardEntity data, Principal principal) {
        Optional<CashCardEntity> cashCard = Optional.ofNullable(cashCardRepository.findByIdAndOwner(id, principal.getName()));
        if (cashCard.isPresent()) {
            CashCardEntity cashCardUpdated = new CashCardEntity(cashCard.get().getId(), data.getAmount(), principal.getName());
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
