package com.bookstore.util;

import com.bookstore.dto.CartItemRequest;
import com.bookstore.model.Book;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;

import java.util.ArrayList;

public class TestUtils {
    public static Book generateBookWithStock(int stock){
        var book = new Book();
        book.setId(1L);
        book.setStock(stock);
        return book;
    }
    public static CartItemRequest generateCartItemRequest(Long customerId,Long bookId, int quantity){
        return new CartItemRequest(customerId,bookId,quantity);
    }
    public static CartItem generateCartItem(Book book,int quantity,Cart cart){
        return new CartItem(book,quantity,cart);
    }

    public static Cart generateCartWithEmptyCartItem(){
            var cart = new Cart();
            cart.setId(1L);
            cart.setCartItems(new ArrayList<>());
            return cart;

    }
}
