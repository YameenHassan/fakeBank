package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name="bank")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_code")
    private Integer bankCode;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(bankName, bank.bankName) && Objects.equals(bankCode, bank.bankCode) && Objects.equals(isActive, bank.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankName, bankCode, isActive);
    }
}
