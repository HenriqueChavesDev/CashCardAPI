package com.henriquechaves.cashcard.controllers;


import com.henriquechaves.cashcard.CashCardRepository;
import com.henriquechaves.cashcard.entities.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }


    @GetMapping
    public ResponseEntity<Page<CashCard>> listCashCards(Pageable pageable){
        Page<CashCard> cashCardPage = cashCardRepository.getPaginatedCashCards(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "amount")));
        return ResponseEntity.ok(cashCardPage);
    }
}
