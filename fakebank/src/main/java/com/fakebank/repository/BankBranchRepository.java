package com.fakebank.repository;

import com.fakebank.model.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    BankBranch findById(Integer Id);
}
