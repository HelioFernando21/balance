package com.bank.balance.domain.availableCreditLimit;

import java.math.BigDecimal;

public record AvailableCreditLimitResponseDTO(Long account_id, BigDecimal available_credit_limit) {
}
