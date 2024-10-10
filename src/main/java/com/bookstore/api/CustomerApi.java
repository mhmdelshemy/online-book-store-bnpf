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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CustomerApi {

    private final CustomerService customerService;

    @PostMapping("register")
    public ResponseEntity<Customer> register(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.registerCustomer(customerDTO));
    }

//    @PostMapping("login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        Customer customer = customerService.authenticateUser(loginRequest.username(), loginRequest.password());
//
////        String token = jwtHandler.generateToken(customer.getUserName());
//        return ResponseEntity.ok(token);
//    }

    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    @PostMapping("login")
    Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userDetailsService.findByUsername(loginRequest.username())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
}
