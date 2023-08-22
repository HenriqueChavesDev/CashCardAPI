package com.henriquechaves.cashcard.repositories;

import com.henriquechaves.cashcard.entities.CashCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashCardRepository extends CrudRepository<CashCardEntity, Long>, PagingAndSortingRepository<CashCardEntity, Long> {
    CashCardEntity findByIdAndOwner(Long id, String owner);
    Page<CashCardEntity> findByOwner(String owner, PageRequest amount);

    boolean existsByIdAndOwner(Long id, String owner);
}
