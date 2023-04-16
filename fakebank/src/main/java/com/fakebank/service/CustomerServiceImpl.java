package com.fakebank.service;

import com.fakebank.model.Customer;
import com.fakebank.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer getCustomerById(Integer id) {
        Customer customers = customerRepository.findById(id);
        return customers;
    }
}
