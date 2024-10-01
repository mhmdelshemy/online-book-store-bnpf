package com.bookstore.service.impl;


import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repo.BookRepository;
import com.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    @Override
    public Book addBook(Book book) {
        log.debug("Saving book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        log.debug("Getting Book by id : {}",id);
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> listBooks() {
        List<Book> books =bookRepository.findAll();
        log.debug("List books, found {} books.", books.size());
        return books;
    }

}
