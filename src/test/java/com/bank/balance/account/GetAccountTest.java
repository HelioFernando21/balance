package com.bank.balance.account;

import com.bank.balance.domain.account.Account;
import com.bank.balance.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class GetAccountTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void reset() {
       accountRepository.deleteAll();
    }

    @Test
    void withSuccess() throws Exception {
        Account account = new Account();
        account.setDocumentNumber("12345678900");
        Account mockedAccount = accountRepository.save(account);

        mockMvc.perform(get("/api/accounts/" + mockedAccount.getId() ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(mockedAccount.getId()))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    void withNotFoundAccount() throws Exception {
        Account account = new Account();
        account.setDocumentNumber("12345678900");
        Account mockedAccount = accountRepository.save(account);

        mockMvc.perform(get("/api/accounts/" + mockedAccount.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found!"));
    }

}
