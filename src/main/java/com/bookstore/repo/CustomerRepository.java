package com.bookstore.repo;

import com.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByUserName(String username);
    Customer findByUserName(String username);
}
