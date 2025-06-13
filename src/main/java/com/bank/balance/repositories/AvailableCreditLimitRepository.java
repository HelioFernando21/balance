package com.bank.balance.repositories;

import com.bank.balance.domain.availableCreditLimit.AvailableCreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AvailableCreditLimitRepository extends JpaRepository<AvailableCreditLimit, Long> {
}
