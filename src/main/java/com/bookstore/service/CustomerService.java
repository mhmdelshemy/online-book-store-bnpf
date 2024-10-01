package com.bookstore.service;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.LoginRequest;
import com.bookstore.model.Customer;

public interface CustomerService {
    Customer registerCustomer(CustomerDTO customerDTO);
    Customer login(LoginRequest loginRequest);
    Customer findById(Long id);
    Customer findByUsername(String username);
    Customer authenticateUser(String username, String password);
    }
