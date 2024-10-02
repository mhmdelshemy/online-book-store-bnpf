package com.bookstore.service;

import com.bookstore.dto.CartDTO;
import com.bookstore.dto.CartItemRequest;
import com.bookstore.model.Cart;

public interface CartService {

    void addBookToCart(CartItemRequest cartItemRequest);
    CartDTO findByCustomerId(Long customerId);

    void removeCart(Long cartId);
    double checkout(Cart cart);
}
