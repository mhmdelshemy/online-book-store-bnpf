package com.bookstore.repo;

import com.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByUserName(String username);
    Mono<Customer> findByUserName(String username);
}
