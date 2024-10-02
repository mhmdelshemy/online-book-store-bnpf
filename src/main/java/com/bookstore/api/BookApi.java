package com.bookstore.api;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${bookstore.api.bookMapping}")
public class BookApi {

    private final BookService bookService;
    @GetMapping("${bookstore.api.listBooks}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }
}
