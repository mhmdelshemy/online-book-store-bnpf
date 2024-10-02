package com.bookstore.service;

import com.bookstore.dto.CartDTO;
import com.bookstore.model.Cart;

public interface CartService {

    void addBookToCart(Long customerId, Long bookId, int quantity);
    CartDTO findByCustomerId(Long customerId);

    void removeCart(Long cartId);
    double checkout(Cart cart);
}
