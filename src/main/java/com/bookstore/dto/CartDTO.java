package com.bookstore.dto;

import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private Long id;
    private Customer customer;
    private List<CartItemDTO> cartItems;
}

