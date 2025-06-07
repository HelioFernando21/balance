package com.bank.balance.account;

import com.bank.balance.domain.account.AccountRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CreateAccountTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void withSuccess() throws Exception {
        AccountRequestDTO newAccountRequestDTO = new AccountRequestDTO("12345678900");

        mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newAccountRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").exists())
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    void withEmptyDocumentNumber() throws Exception {
        AccountRequestDTO newAccountRequestDTO = new AccountRequestDTO("");

        mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newAccountRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid document_number!"));
    }

    @Test
    void withNullDocumentNumber() throws Exception {
        AccountRequestDTO newAccountRequestDTO = new AccountRequestDTO(null);

        mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newAccountRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid document_number!"));
    }

    @Test
    void withLimitExceededDocumentNumber() throws Exception {
        AccountRequestDTO newAccountRequestDTO = new AccountRequestDTO("123456789000");

        mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newAccountRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid document_number!"));
    }

}
