package com.bookstore.service.impl;

import com.bookstore.dto.CartDTO;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.repo.CartRepository;
import com.bookstore.service.BookService;
import com.bookstore.service.CartItemService;
import com.bookstore.service.CartService;
import com.bookstore.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final BookService bookService;
    private final CartItemService cartItemService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CustomerService customerService, BookService bookService,
                           @Lazy CartItemService cartItemService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void addBookToCart(Long customerId, Long bookId, int quantity) {
        var book = bookService.findById(bookId);
        if(book.getStock() < quantity){
            //TODO
            throw new RuntimeException();
        }
        var customer = customerService.findById(customerId);
        var cart = cartRepository.findByCustomerId(customerId);
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }

        var cartItem = cartItemService.findByBook(book);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            cartItem = new CartItem(book, quantity);
        }
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO findByCustomerId(Long customerId) {

        return modelMapper.map(cartRepository.findByCustomerId(customerId),CartDTO.class);
    }

    @Override
    public void removeCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    public void checkout(Cart cart){

        var  checkedCart = cartRepository.findById(cart.getId()).orElseThrow(RuntimeException::new);

        for(CartItem cartItem: checkedCart.getCartItems()){
            var book = cartItem.getBook();
            book.setStock(book.getStock()-cartItem.getQuantity());
            bookService.addBook(book);
        }

    }

}
