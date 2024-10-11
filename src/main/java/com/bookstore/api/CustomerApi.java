package com.bookstore.api;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.LoginRequest;
import com.bookstore.dto.LoginResponse;
import com.bookstore.model.Customer;
import com.bookstore.security.TokenProvider;
import com.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CustomerApi {

    private final CustomerService customerService;

//    @PostMapping("register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<Customer> register(@RequestBody CustomerDTO customerDTO) {
//        return customerService.registerCustomer(customerDTO);
//    }

//    @PostMapping("login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        Customer customer = customerService.authenticateUser(loginRequest.username(), loginRequest.password());
//
////        String token = jwtHandler.generateToken(customer.getUserName());
//        return ResponseEntity.ok(token);
//    }

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("login")
    Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return customerService.findByUsername(loginRequest.username())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
}
