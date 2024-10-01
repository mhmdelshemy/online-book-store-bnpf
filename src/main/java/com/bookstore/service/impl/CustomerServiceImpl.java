package com.bookstore.service.impl;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.LoginRequest;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Customer;
import com.bookstore.repo.CustomerRepository;
import com.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    @Override
    public Customer registerCustomer(CustomerDTO customerDTO) {
        if(customerRepository.existsByUserName(customerDTO.userName())){
            throw new CustomerNotFoundException();
        }
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.firstName());
        customer.setSurName(customerDTO.surName());
        customer.setUserName(customerDTO.userName());
        String hashedPassword = passwordEncoder.encode(customerDTO.password());
        customer.setPassword(hashedPassword);
        return customerRepository.save(customer);
    }

    @Override
    public Customer login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUserName(username);
    }
    @Override
    public Customer authenticateUser(String username, String password) {
        Customer user = findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }
}
