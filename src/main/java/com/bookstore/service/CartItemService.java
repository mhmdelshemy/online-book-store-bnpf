package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.dto.CartItemRequest;

public interface CartItemService {

    CartItem addCartItem(CartItem cartItem);
     CartItem findByBook(Book book);
    void removeCartItem(CartItem cartItem);
    void modifyCartItem(CartItemRequest cartItemRequest);
}
