package com.henriquechaves.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {
    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository repository){
        this.cashCardRepository = repository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId){

        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        if (cashCardOptional.isPresent()) {
            return ResponseEntity.ok(cashCardOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity createCashCard(@RequestBody CashCard newCashCard, UriComponentsBuilder ucb){
        CashCard savedCashCard = cashCardRepository.save(newCashCard);
        URI locationOfNewCashCard = ucb.path("api/v1/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable){
        Page<CashCard> page = cashCardRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return ResponseEntity.ok(page.getContent());
    }
}
