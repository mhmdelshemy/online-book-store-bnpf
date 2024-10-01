package com.bookstore.dto;

public record CustomerDTO(
        String firstName,
        String surName,
        String userName,
        String password) {
}
