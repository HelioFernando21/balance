package com.bank.balance.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(Long account_id, Long operation_type_id, BigDecimal amount, LocalDateTime event_date) {
}