package com.bank.balance.repositories;

import com.bank.balance.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
