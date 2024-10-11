package com.bookstore.service.impl;

import com.bookstore.dto.LoginRequest;
import com.bookstore.model.Customer;
import com.bookstore.repo.CustomerRepository;
import com.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
//    @Override
//    public Mono<Customer> registerCustomer(CustomerDTO customerDTO) {
//        if(customerRepository.existsByUserName(customerDTO.userName())){
//            throw new CartNotFoundException();
//        }
//        Customer customer = new Customer();
//        customer.setFirstName(customerDTO.firstName());
//        customer.setSurName(customerDTO.surName());
//        customer.setUserName(customerDTO.userName());
//        String hashedPassword = passwordEncoder.encode(customerDTO.password());
//        customer.setPassword(hashedPassword);
//        return customerRepository.save(customer);
//    }

    @Override
    public Customer login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Mono<Customer> findById(Long id) {
        return Mono.fromCallable(() -> customerRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalCustomer ->
                        optionalCustomer.map(Mono::just).orElseGet(Mono::empty)
                );
    }

    @Override
    public Mono<Customer> findByUsername(String username) {
        return Mono.fromCallable(() -> customerRepository.findByUserName(username)).subscribeOn(Schedulers.boundedElastic()).block();
    }
//    @Override
//    public Customer authenticateUser(String username, String password) {
//        Customer user = findByUsername(username);
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//        return user;
//    }
}
