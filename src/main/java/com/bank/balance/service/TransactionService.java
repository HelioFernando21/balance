package com.bank.balance.service;

import com.bank.balance.domain.account.Account;
import com.bank.balance.domain.availableCreditLimit.AvailableCreditLimit;
import com.bank.balance.domain.operationType.OperationTypeEnum;
import com.bank.balance.domain.transaction.Transaction;
import com.bank.balance.domain.transaction.TransactionRequestDTO;
import com.bank.balance.domain.transaction.TransactionResponseDTO;
import com.bank.balance.exception.BadRequestException;
import com.bank.balance.repositories.AccountRepository;
import com.bank.balance.repositories.AvailableCreditLimitRepository;
import com.bank.balance.repositories.TransactionRepository;
import com.bank.balance.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AvailableCreditLimitRepository availableCreditLimitRepository;

    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
        this.validateTransactionRequestDTO(transactionRequestDTO);

        BigDecimal transactionAmount = this.convertTransactionAmountValue(transactionRequestDTO.amount(), transactionRequestDTO.operation_type_id());

        this.discountAccountBalance(transactionRequestDTO.account_id(), transactionAmount);

        Transaction newTransaction = this.convertToTransaction(transactionRequestDTO, transactionAmount);

        Transaction saved = this.transactionRepository.save(newTransaction);
        System.out.printf("[TransactionService.create] Successfully completed - transaction : %s%n", saved);

        return new TransactionResponseDTO(
                saved.getAccountId(),
                saved.getOperationTypeId(),
                saved.getAmount(),
                saved.getEventDate()
        );
    }

    private void discountAccountBalance(Long accountId, BigDecimal transactionAmount) {
        Optional<AvailableCreditLimit> optionalAccount = availableCreditLimitRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new BadRequestException("Invalid account_id!");
        }

        AvailableCreditLimit availableCreditLimit =  optionalAccount.get();
        BigDecimal currentBalance = availableCreditLimit.getAvailableCreditLimit();
        currentBalance = currentBalance.add(transactionAmount);
        if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Insufficient balance!");
        }

        AvailableCreditLimit availableCreditLimitToSave = new AvailableCreditLimit(accountId, currentBalance);
        this.availableCreditLimitRepository.save(availableCreditLimitToSave);
    }

    private Transaction convertToTransaction(TransactionRequestDTO transactionRequestDTO, BigDecimal transactionAmount) {
        System.out.println("[TransactionService.convertToTransaction] Start converting to transaction!");
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionAmount);
        transaction.setAccountId(transactionRequestDTO.account_id());
        transaction.setOperationTypeId(transactionRequestDTO.operation_type_id());
        transaction.setEventDate(DateUtils.now());
        return transaction;
    }

    private void validateTransactionRequestDTO(TransactionRequestDTO transactionRequestDTO) {
        System.out.println("[TransactionService.validateTransactionRequestDTO] Start validation!");

        if (transactionRequestDTO.operation_type_id() == null) {
            throw new BadRequestException("Invalid operation_type_id!");
        }

        if (!OperationTypeEnum.contains(transactionRequestDTO.operation_type_id())) {
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

        System.out.println("[TransactionService.validateTransactionRequestDTO] Validation completed!");
    }

    private BigDecimal convertTransactionAmountValue(BigDecimal amount, Long operationTypeId) {
        System.out.println("[TransactionService.convertTransactionAmountValue] Start converting amount value!");
        if (amount.signum() < 0) {
            amount = amount.abs();
        }

        if (operationTypeId.equals(OperationTypeEnum.CREDIT_VOUCHER.getId())) {
            return amount.abs();
        }

        return amount.negate();
    }
}
