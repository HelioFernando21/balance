package com.bank.balance.domain.availableCreditLimit;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "availablecreditlimits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AvailableCreditLimit {
    @Id
    @Column(name = "account_ID")
    private Long accountId;

    @Column(name = "Available_Credit_Limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal availableCreditLimit;
}
