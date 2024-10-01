package com.bookstore.dto;

public record CartItemRequest (

     Long customerId,
     Long bookId,
     int quantity){}

