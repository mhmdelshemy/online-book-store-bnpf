package com.bookstore.dto;

import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;

import java.util.List;

public record CartDTO (

     Long id,
     Customer customer,
     List<CartItem> cartItems){}

