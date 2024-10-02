package com.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemRequest{

     private Long customerId;
     private Long bookId;
     private int quantity;
}

