package com.fakebank.repository;

import com.fakebank.model.AccountType;
import com.fakebank.model.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    AccountType findById(Integer Id);
}
