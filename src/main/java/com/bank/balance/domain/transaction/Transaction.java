package com.bank.balance.domain.transaction;

import com.bank.balance.domain.operationType.OperationType;
import com.bank.balance.domain.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_ID")
    private Long id;

    @Column(name = "account_ID")
    private Long accountId;

    @Column(name = "operationtype_ID")
    private Long operationTypeId;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "eventdate")
    private LocalDateTime eventDate;

//    @ManyToOne
//    @MapsId
//    @JoinColumn(name = "Account_ID")
//    private Account account;
}
