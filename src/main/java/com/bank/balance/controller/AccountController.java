package com.bank.balance.controller;

import com.bank.balance.domain.account.AccountRequestDTO;
import com.bank.balance.domain.account.AccountResponseDTO;
import com.bank.balance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO accountRequestDTO) {
        System.out.printf("[AccountController.create] Start creation - account: %s%n", accountRequestDTO);
        AccountResponseDTO newAccount = this.accountService.create(accountRequestDTO);
        System.out.printf("[AccountController.create] Successfully completed - %s%n", newAccount);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getById(@PathVariable Long id) {
        System.out.printf("[AccountController.getById] Start get - id: %s%n", id);
        AccountResponseDTO newAccount = this.accountService.getById(id);
        System.out.printf("[AccountController.getById] Successfully completed - %s%n", newAccount);
        return ResponseEntity.ok(newAccount);
    }
}
