package com.fakebank.repository;

import com.fakebank.model.Transaction;
import com.fakebank.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    TransactionType findByTransactionType(String type);
}