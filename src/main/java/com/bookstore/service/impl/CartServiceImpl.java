package com.bookstore.service.impl;

import com.bookstore.dto.CartDTO;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.OutOfStockException;
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
        if(book.getStock() < quantity)
            throw new OutOfStockException();

        var customer = customerService.findById(customerId);
        var cart = cartRepository.findByCustomerId(customerId);
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
        }
        cartRepository.save(cart);

        var cartItem = cartItemService.findByCartAndBook(cart,book);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            cartItem = new CartItem(book, quantity,cart);
        }
        cartItemService.addCartItem(cartItem);
    }

    @Override
    public CartDTO findByCustomerId(Long customerId) {
        var cart = cartRepository.findByCustomerId(customerId);
        if(cart ==null)
            throw new CartNotFoundException();

        return modelMapper.map(cart,CartDTO.class);
    }

    @Override
    public void removeCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    @Override
    @Transactional
    public double checkout(Cart cart){
        var totalToPay = 0.0;
        for(CartItem cartItem: cart.getCartItems()){
            var book = cartItem.getBook();
            totalToPay+=(book.getPrice()*cartItem.getQuantity());
            book.setStock(book.getStock()-cartItem.getQuantity());
            bookService.addBook(book);
        }
        removeCart(cart.getId());
        return totalToPay;

    }

}
