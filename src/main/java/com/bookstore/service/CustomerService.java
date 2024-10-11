package com.bookstore.service;

import com.bookstore.dto.LoginRequest;
import com.bookstore.model.Customer;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CustomerService {
  //  Mono<Customer> registerCustomer(CustomerDTO customerDTO);
    Customer login(LoginRequest loginRequest);
    Mono<Customer> findById(Long id);
    Mono<Customer> findByUsername(String username);
//    Customer authenticateUser(String username, String password);
    }
