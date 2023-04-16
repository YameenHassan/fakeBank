package com.fakebank.repository;

import com.fakebank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //@Query(value ="from fake_bank.transaction as t where t.account_number = :accountNumber and t.created_on between :fromDate and :toDate")
    Set<Transaction> findByAccountNumberAndCreatedOnBetween(String accountNumber, Date fromDate, Date toDate);
}