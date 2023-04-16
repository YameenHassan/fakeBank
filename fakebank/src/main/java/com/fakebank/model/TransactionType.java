package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction_type")
@Data
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionType that = (TransactionType) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType);
    }
}
