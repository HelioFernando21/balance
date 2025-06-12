package com.bank.balance.transaction;

import com.bank.balance.domain.account.Account;
import com.bank.balance.domain.operationType.OperationType;
import com.bank.balance.domain.transaction.TransactionRequestDTO;
import com.bank.balance.repositories.AccountRepository;
import com.bank.balance.repositories.OperationTypeRepository;
import com.bank.balance.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CreateTransactionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    private Account mockedAccount;

    @BeforeEach()
    public void init() {
        if (mockedAccount != null ) {
            return;
        }
        accountRepository.deleteAll();

        Account account = new Account();
        account.setDocumentNumber("12345678900");
        mockedAccount = accountRepository.save(account);

        List<OperationType> mockedOperationsTypes = Arrays.asList(
                new OperationType(null, "Normal Purchase", LocalDateTime.now()),
                new OperationType(null, "Purchase with installments", LocalDateTime.now()),
                new OperationType(null, "Withdrawal", LocalDateTime.now()),
                new OperationType(null, "Credit Voucher", LocalDateTime.now())
        );
        operationTypeRepository.saveAll(mockedOperationsTypes);
    }

    @Test
    void withNormalPurchaseSuccess() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                1L,
                new BigDecimal("50.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account_id").value(mockedAccount.getId()))
                    .andExpect(jsonPath("$.operation_type_id").value(1L))
                    .andExpect(jsonPath("$.amount").value(-50.21))
                    .andExpect(jsonPath("$.event_date").value("2025-06-07T15:30:00"));
        }
    }

    @Test
    void withPurchaseInstallmentsSuccess() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                2L,
                new BigDecimal("51.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account_id").value(mockedAccount.getId()))
                    .andExpect(jsonPath("$.operation_type_id").value(2L))
                    .andExpect(jsonPath("$.amount").value(-51.21))
                    .andExpect(jsonPath("$.event_date").value("2025-06-07T15:30:00"));
        }
    }

    @Test
    void withWithdrawalSuccess() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                3L,
                new BigDecimal("52.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account_id").value(mockedAccount.getId()))
                    .andExpect(jsonPath("$.operation_type_id").value(3L))
                    .andExpect(jsonPath("$.amount").value(-52.21))
                    .andExpect(jsonPath("$.event_date").value("2025-06-07T15:30:00"));
        }
    }

    @Test
    void withCreditVoucherSuccess() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                4L,
                new BigDecimal("53.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account_id").value(mockedAccount.getId()))
                    .andExpect(jsonPath("$.operation_type_id").value(4L))
                    .andExpect(jsonPath("$.amount").value(53.21))
                    .andExpect(jsonPath("$.event_date").value("2025-06-07T15:30:00"));
        }
    }

    @Test
    void withEmptyAccountId() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                null,
                4L,
                new BigDecimal("53.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid account_id!"));
        }
    }

    @Test
    void withInvalidAccountId() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                (mockedAccount.getId() + 1),
                4L,
                new BigDecimal("53.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid account_id!"));
        }
    }

    @Test
    void withEmptyOperationTypeId() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                null,
                new BigDecimal("53.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid operation_type_id!"));
        }
    }

    @Test
    void withInvalidOperationTypeId() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                5L,
                new BigDecimal("53.21")
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid operation_type_id!"));
        }
    }

    @Test
    void withInvalidAmount() throws Exception {
        TransactionRequestDTO newTransactionRequestDTO = new TransactionRequestDTO(
                mockedAccount.getId(),
                4L,
                null
        );

        try (MockedStatic<DateUtils> mockedStatic = Mockito.mockStatic(DateUtils.class)) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2025, 6, 7, 15, 30, 0);
            mockedStatic.when(DateUtils::now).thenReturn(fixedDateTime);

            mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newTransactionRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Invalid amount!"));
        }
    }

}
