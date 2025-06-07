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
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountResponseDTO create(AccountRequestDTO data) {
        this.validateAccount(data);

        Account savedAccount = this.saveAccount(data.document_number());

        return new AccountResponseDTO(savedAccount.getId(), savedAccount.getDocumentNumber());
    }

    public AccountResponseDTO getById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        Account account = optionalAccount.orElseThrow(() -> new NotFoundException("Account not found!"));

        return new AccountResponseDTO(account.getId(), account.getDocumentNumber());
    }

    private void validateAccount(AccountRequestDTO data) {
        if (data.document_number() == null || data.document_number().isEmpty() || data.document_number().length() > 11) {
            throw new BadRequestException("Invalid document_number!");
        }
    }

    private Account saveAccount(String documentNumber) {
        Account newAccount = new Account();
        newAccount.setDocumentNumber(documentNumber);
        newAccount.setCreated(LocalDateTime.now());

        return accountRepository.save(newAccount);
    }
}
