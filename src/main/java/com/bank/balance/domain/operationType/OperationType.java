package com.bank.balance.domain.operationType;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="OperationsTypes")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operationtype_ID")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private LocalDateTime created;
}
