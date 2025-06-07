package com.bank.balance.service;

import com.bank.balance.domain.account.Account;
import com.bank.balance.domain.transaction.Transaction;
import com.bank.balance.domain.transaction.TransactionRequestDTO;
import com.bank.balance.domain.transaction.TransactionResponseDTO;
import com.bank.balance.exception.BadRequestException;
import com.bank.balance.exception.NotFoundException;
import com.bank.balance.repositories.AccountRepository;
import com.bank.balance.repositories.TransactionRepository;
import com.bank.balance.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
        validateTransactionRequestDTO(transactionRequestDTO);

        BigDecimal transactionAmount = convertTransactionAmountValue(transactionRequestDTO.amount(), transactionRequestDTO.operation_type_id());

        Transaction newTransaction = convertToTransaction(transactionRequestDTO, transactionAmount);

        Transaction saved = transactionRepository.save(newTransaction);

        return new TransactionResponseDTO(
                saved.getAccountId(),
                saved.getOperationTypeId(),
                saved.getAmount(),
                saved.getEventDate()
        );
    }

    private Transaction convertToTransaction(TransactionRequestDTO transactionRequestDTO, BigDecimal transactionAmount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionAmount);
        transaction.setAccountId(transactionRequestDTO.account_id());
        transaction.setOperationTypeId(transactionRequestDTO.operation_type_id());
        transaction.setEventDate(DateUtils.now());
        return transaction;
    }

    private void validateTransactionRequestDTO(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO.operation_type_id() == null) {
            throw new BadRequestException("Invalid operation_type_id!");
        }

        if (transactionRequestDTO.operation_type_id() < 1 || transactionRequestDTO.operation_type_id() > 4) {
            throw new BadRequestException("Invalid operation_type_id!");
        }

        if (transactionRequestDTO.amount() == null) {
            throw new BadRequestException("Invalid amount!");
        }

        if (transactionRequestDTO.account_id() == null) {
            throw new BadRequestException("Invalid account_id!");
        }

        Optional<Account> optionalAccount = accountRepository.findById(transactionRequestDTO.account_id());
        if (optionalAccount.isEmpty()) {
            throw new BadRequestException("Invalid account_id!");
        }
    }

    private BigDecimal convertTransactionAmountValue(BigDecimal amount, Long operationTypeId) {
        if (amount.signum() < 0) {
            amount = amount.abs();
        }

        if (operationTypeId.equals(4L)) {
            return amount.abs();
        }

        return amount.negate();
    }
}
