package com.bank.balance.repositories;

import com.bank.balance.domain.operationType.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
