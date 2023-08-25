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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CashCardControllerUnityTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CashCardRepository cashCardRepository;

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

    @Test
    void shouldBeReturnOnlyOneCashCardWhenReceiveID() throws Exception {
        var result = new CashCard(7L, 120.88, "henrique");
        given(cashCardRepository.findById(anyLong())).willReturn(Optional.of(result));
        mockMvc.perform(get("/api/v1/cashcards/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(7L));
    }
    @Test
     void shouldResponseNotFoundWhenReceiveIdCashCardNotExist() throws Exception {
        given(cashCardRepository.findById(222L)).willReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/cashcards/222"))
                .andExpect(status().isNotFound());
     }

     @Test
    void shouldDeleteCashCardByIdIsSuccess() throws Exception {
         given(cashCardRepository.existsById(1L)).willReturn(true);
         mockMvc.perform(delete("/api/v1/cashcards/1"))
                .andExpect(status().isNoContent());

         verify(cashCardRepository).existsById(1L);
         verify(cashCardRepository).deleteById(1L);
    }

    @Test
    void shouldDeleteCashCardByIdIsFail() throws Exception {
        given(cashCardRepository.existsById(178L)).willReturn(false);
        mockMvc.perform(delete("/api/v1/cashcards/178"))
                .andExpect(status().isNotFound());

        verify(cashCardRepository).existsById(178L);
        verify(cashCardRepository, times(0)).deleteById(178L);
    }

    @Test
    void shouldBeUpdatedCashCardWithSuccess() throws Exception {
        var card = new CashCard(23L,239.99, "henrique");
        var cardResult = new CashCard(23L,239.99, "henrique");
        given(cashCardRepository.existsById(23L)).willReturn(true);
        given(cashCardRepository.save(card)).willReturn(cardResult);
        mockMvc.perform(put("/api/v1/cashcards/23")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CashCardTestHelper.parseJsonTo(card)))
                .andExpect(status().isNoContent());

        verify(cashCardRepository).existsById(anyLong());
        verify(cashCardRepository).save(any(CashCard.class));
    }

    @Test
    void shouldBeUpdatedCashCardIsFailWhenCashCardNotFound() throws Exception {
        var card = new CashCard(100L,1239.99, "henrique");
        given(cashCardRepository.existsById(100L)).willReturn(false);
        mockMvc.perform(put("/api/v1/cashcards/100")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CashCardTestHelper.parseJsonTo(card)))
                .andExpect(status().isNotFound());

        verify(cashCardRepository).existsById(anyLong());
        verify(cashCardRepository, times(0)).save(any(CashCard.class));
    }

    @Test
    void shouldBeUpdatedCashCardIsFailWhenRequestBodyIsEmpty() throws Exception {
        mockMvc.perform(put("/api/v1/cashcards/100")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(cashCardRepository, times(0)).existsById(anyLong());
        verify(cashCardRepository, times(0)).save(any(CashCard.class));
    }
}
