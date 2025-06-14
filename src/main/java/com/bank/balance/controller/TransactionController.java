package com.bank.balance.controller;

import com.bank.balance.domain.transaction.TransactionRequestDTO;
import com.bank.balance.domain.transaction.TransactionResponseDTO;
import com.bank.balance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        System.out.printf("[TransactionController.create] Start creation - transaction: %s%n", transactionRequestDTO);
        TransactionResponseDTO transactionResponseDTO = this.transactionService.create(transactionRequestDTO);
        System.out.printf("[TransactionController.create] Successfully completed - %s%n", transactionResponseDTO);
        return ResponseEntity.ok(transactionResponseDTO);
    }
}
