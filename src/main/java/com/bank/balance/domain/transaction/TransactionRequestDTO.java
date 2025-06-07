package com.bank.balance.domain.transaction;

import java.math.BigDecimal;

public record TransactionRequestDTO(Long account_id, Long operation_type_id, BigDecimal amount) {
}