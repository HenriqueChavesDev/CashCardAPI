package com.henriquechaves.cashcard.controllers;


import com.henriquechaves.cashcard.dtos.CreateCashCardDTO;
import com.henriquechaves.cashcard.repositories.CashCardRepository;
import com.henriquechaves.cashcard.entities.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }


    @GetMapping
    public ResponseEntity<Page<CashCard>> listCashCards(Pageable pageable) {
        Page<CashCard> cashCardPage = cashCardRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "amount")));
        return ResponseEntity.ok(cashCardPage);
    }

    @PostMapping
    private ResponseEntity<Void> createNewCashCard(CreateCashCardDTO data) {
        CashCard cashCard = cashCardRepository.save(new CashCard(data.amount(), data.owner()));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cashCard.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CashCard> getCashCardById(@PathVariable Long id) {
        Optional<CashCard> cashCard = cashCardRepository.findById(id);
        return cashCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> deleteCashCardById(@PathVariable Long id) {
        boolean idExists = cashCardRepository.existsById(id);
        if (idExists) {
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
