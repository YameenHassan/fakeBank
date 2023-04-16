package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name="bank_branch")
@Data
public class BankBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bank bank;

    @Column(name = "branch_code")
    private Integer branchCode;

    @Column(name = "address")
    private String address;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankBranch that = (BankBranch) o;
        return Objects.equals(id, that.id) && Objects.equals(bank, that.bank) && Objects.equals(branchCode, that.branchCode) && Objects.equals(address, that.address) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bank, branchCode, address, isActive);
    }
}
