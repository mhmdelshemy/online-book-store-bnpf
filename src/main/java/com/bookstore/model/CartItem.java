package com.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @ToString.Exclude
    private Cart cart;

    public CartItem(Book book, int quantity,Cart cart) {
        this.book = book;
        this.quantity = quantity;
        this.cart = cart;
    }
}
