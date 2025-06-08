package com.bank.balance.domain.account;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="Accounts")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_ID")
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "created")
    private LocalDateTime created;
}
