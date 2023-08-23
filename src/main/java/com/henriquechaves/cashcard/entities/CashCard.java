package com.henriquechaves.cashcard.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String owner;

    public CashCard() {
    }

    public CashCard(Double amount, String owner) {
        this.amount = amount;
        this.owner = owner;
    }

    public CashCard(Long id, Double amount, String owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getOwner() {
        return owner;
    }

}
