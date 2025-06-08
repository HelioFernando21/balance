package com.bank.balance.service;

import com.bank.balance.domain.account.Account;
import com.bank.balance.domain.account.AccountRequestDTO;
import com.bank.balance.domain.account.AccountResponseDTO;
import com.bank.balance.exception.BadRequestException;
import com.bank.balance.exception.NotFoundException;
import com.bank.balance.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountResponseDTO create(AccountRequestDTO data) {
        this.validateAccount(data);

        Account savedAccount = this.saveAccount(data.document_number());

        System.out.printf("[AccountService.create] Successfully completed - account : %s%n", savedAccount);
        return new AccountResponseDTO(savedAccount.getId(), savedAccount.getDocumentNumber());
    }

    public AccountResponseDTO getById(Long accountId) {
        System.out.printf("[AccountService.getById] Find by id - accountId : %s%n", accountId);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        Account account = optionalAccount.orElseThrow(() -> new NotFoundException("Account not found!"));

        System.out.printf("[AccountService.getById] Successfully completed - account : %s%n", account);
        return new AccountResponseDTO(account.getId(), account.getDocumentNumber());
    }

    private void validateAccount(AccountRequestDTO data) {
        System.out.println("[AccountService.validateAccount] Start validation!");
        if (data.document_number() == null || data.document_number().isEmpty() || data.document_number().length() > 11) {
            throw new BadRequestException("Invalid document_number!");
        }
        System.out.println("[AccountService.validateAccount] Validation completed!");
    }

    private Account saveAccount(String documentNumber) {
        System.out.println("[AccountService.saveAccount] Start account saving!");
        Account newAccount = new Account();
        newAccount.setDocumentNumber(documentNumber);
        newAccount.setCreated(LocalDateTime.now());

        return accountRepository.save(newAccount);
    }
}
