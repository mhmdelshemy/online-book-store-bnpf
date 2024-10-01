package com.bookstore.api;

import com.bookstore.dto.CartDTO;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book/")
public class BookApi {

    private final BookService bookService;
    @GetMapping("listbooks")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }
}
