package com.henriquechaves.cashcard.controllers.unity;

import com.henriquechaves.cashcard.CashCardRepository;
import com.henriquechaves.cashcard.dtos.CashCardOutputDTO;
import com.henriquechaves.cashcard.entities.CashCard;
import com.henriquechaves.cashcard.utils.CashCardHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CashCardControllerUnityTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CashCardRepository cashCardRepository;

    @Test
    void shouldBeReturnListOfCashCardsWithSuccess() throws Exception {
        var cashCardPage = new PageImpl<CashCard>(CashCardHelper.list, PageRequest.of(0,10), CashCardHelper.list.size());
        given(cashCardRepository.getPaginatedCashCards(any())).willReturn(cashCardPage);
        mockMvc.perform(get("/api/v1/cashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andReturn();
    }
}
