package com.bookstore.service.impl;

import com.bookstore.dto.CartDTO;
import com.bookstore.dto.CartItemRequest;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
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
import reactor.core.publisher.Mono;

import java.util.Optional;

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

    /**
     * Adds a book to the cart for a given customer.
     * called if the cart not exist for the specified user, and add new cart
     *
     * @param cartItemRequest ->
     *  customerId the ID of the customer
     *  bookId the ID of the book to add
     *  quantity the number of books to add
     */
    @Override
    @Transactional
    public void addBookToCart(CartItemRequest cartItemRequest) {
        var book = bookService.findById(cartItemRequest.getBookId());
        if(book.getStock() < cartItemRequest.getQuantity())
            throw new OutOfStockException();

        var customer = customerService.findById(cartItemRequest.getCustomerId());
        var cart = cartRepository.findByCustomerId(cartItemRequest.getCustomerId());
        log.debug("Cart : {}", cart);

        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
        }
        cartRepository.save(cart);

        var cartItem = cartItemService.findByCartAndBook(cart,book);
        if (cartItem != null) {
            cartItem.setQuantity(cartItemRequest.getQuantity());
        } else {
            cartItem = new CartItem(book, cartItemRequest.getQuantity(),cart);
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

    /**
     * checkout the cart for specific user
     * remve the cart associated to the user
     *
     * @param cart the cart to checkout
     */
    @Override
    @Transactional
    public double checkout(Cart cart){
        var totalToPay = 0.0;
        log.debug("Cart : {}", cart);
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
