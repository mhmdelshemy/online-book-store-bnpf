package com.bookstore.dto;

import com.bookstore.model.Book;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Book book;
    private int quantity;
}
