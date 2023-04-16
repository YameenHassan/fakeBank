package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name="transaction")
@Data
public class SubTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TransactionType transactionType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(name = "credit_debit")
    private String creditDebit;

    @Column(name = "current_balance")
    private Float currentBalance;

    @Column(name = "created_on")
    private Date createdOn;


}
