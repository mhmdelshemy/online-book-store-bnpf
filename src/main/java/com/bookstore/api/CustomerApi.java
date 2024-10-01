package com.bookstore.api;

import com.bookstore.config.JWTHandler;
import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.LoginRequest;
import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/customer/")
public class CustomerApi {

    private final CustomerService customerService;
    private final JWTHandler jwtHandler;


    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.registerCustomer(customerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Customer customer = customerService.authenticateUser(loginRequest.username(), loginRequest.password());

        String token = jwtHandler.generateToken(loginRequest.username());
        return ResponseEntity.ok(token);
    }
}
