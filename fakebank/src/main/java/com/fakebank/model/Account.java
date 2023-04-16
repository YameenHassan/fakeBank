package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private Float balance;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankBranch bankBranch;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AccountType accountType;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "last_updated_on")
    private Date lastUpdatedOn;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(customer, account.customer) && Objects.equals(bankBranch, account.bankBranch) && Objects.equals(accountType, account.accountType) && Objects.equals(currency, account.currency) && Objects.equals(createdOn, account.createdOn) && Objects.equals(lastUpdatedOn, account.lastUpdatedOn) && Objects.equals(isActive, account.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance, customer, bankBranch, accountType, currency, createdOn, lastUpdatedOn, isActive);
    }
}
