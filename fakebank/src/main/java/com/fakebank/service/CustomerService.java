package com.fakebank.service;

import com.fakebank.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(Integer id);
}
