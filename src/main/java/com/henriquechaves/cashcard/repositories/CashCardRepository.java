package com.henriquechaves.cashcard.repositories;

import com.henriquechaves.cashcard.entities.CashCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long>,PagingAndSortingRepository<CashCard, Long> {}
