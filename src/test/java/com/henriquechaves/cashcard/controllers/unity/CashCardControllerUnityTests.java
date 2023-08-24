package com.henriquechaves.cashcard.controllers.unity;

import com.henriquechaves.cashcard.dtos.CreateCashCardDTO;
import com.henriquechaves.cashcard.repositories.CashCardRepository;
import com.henriquechaves.cashcard.entities.CashCard;
import com.henriquechaves.cashcard.utils.CashCardTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        var cashCardPage = new PageImpl<CashCard>(CashCardTestHelper.list, PageRequest.of(0,10), CashCardTestHelper.list.size());
        given(cashCardRepository.findAll(any(PageRequest.class))).willReturn(cashCardPage);
        mockMvc.perform(get("/api/v1/cashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andReturn();
    }

    @Test
    void shouldCreateNewCashCardIsSuccess() throws Exception {
        var newCashCard = new CreateCashCardDTO(22.11, "henrique");
        var result = new CashCard(32L, 22.11, "henrique");

        given(cashCardRepository.save(any(CashCard.class))).willReturn(result);
        mockMvc.perform(post("/api/v1/cashcards")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(CashCardTestHelper.parseJsonTo(newCashCard)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/v1/cashcards/32"));
    }
}
