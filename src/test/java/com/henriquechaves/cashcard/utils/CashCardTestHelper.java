package com.henriquechaves.cashcard.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquechaves.cashcard.entities.CashCard;

import java.util.List;

public class CashCardTestHelper {
    public static final List<CashCard> list = List.of(
            new CashCard(1L, 199.99, "henrique"),
            new CashCard(2L, 299.99, "henrique"),
            new CashCard(3L, 399.99, "henrique"),
            new CashCard(4L, 499.99, "henrique"),
            new CashCard(5L, 599.99, "henrique"),
            new CashCard(6L, 699.99, "henrique"),
            new CashCard(7L, 799.99, "henrique"),
            new CashCard(8L, 899.99, "henrique"),
            new CashCard(9L, 899.99, "henrique"),
            new CashCard(10L, 1999.99, "henrique")

    );

    public static String parseJsonTo(Object data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }
}
