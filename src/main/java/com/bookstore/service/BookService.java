package com.bookstore.service;

import com.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);

    Book findById(Long id);

    List<Book> listBooks();
}

