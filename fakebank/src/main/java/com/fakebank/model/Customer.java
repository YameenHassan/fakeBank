package com.fakebank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name="customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "is_active")
    private Boolean isActive;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(nationalId, customer.nationalId) && Objects.equals(email, customer.email) && Objects.equals(phone, customer.phone) && Objects.equals(address, customer.address) && Objects.equals(isActive, customer.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nationalId, email, phone, address, isActive);
    }
}
