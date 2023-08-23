package com.henriquechaves.cashcard;

import com.henriquechaves.cashcard.entities.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashCardRepository extends JpaRepository<CashCard, Long> {
    Page<CashCard> getPaginatedCashCards(PageRequest pageRequest);
}
