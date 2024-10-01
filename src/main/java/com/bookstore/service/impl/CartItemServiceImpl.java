package com.bookstore.service.impl;

import com.bookstore.dto.CartItemRequest;
import com.bookstore.exception.InvalidQuantityException;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.repo.CartItemRepository;
import com.bookstore.repo.CartRepository;
import com.bookstore.service.BookService;
import com.bookstore.service.CartItemService;
import com.bookstore.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final BookService bookService;

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        log.debug("Saving CartItem : {}", cartItem);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem findByBook(Book book) {
        return cartItemRepository.findByBook(book);
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
        cartItemRepository.flush();
    }

    @Override
    @Transactional
    public void modifyCartItem(CartItemRequest cartItemRequest){

        int quantity = cartItemRequest.quantity();
        if(quantity < 0){
            //TODO
            throw new InvalidQuantityException();
        }
        var book = bookService.findById(cartItemRequest.bookId());

        if (quantity > book.getStock()) {
            throw new RuntimeException("no av stock");
        }

        var cart = cartRepository.findByCustomerId(cartItemRequest.customerId());
        if (cart == null) {
            throw new RuntimeException("Cart not found for logged user, create cart first");
        }

        var cartItem = findByBook(book);
        if (cartItem != null) {
            if (quantity == 0){
                removeCartItem(cartItem);
                cart.getCartItems().remove(cartItem);
                cartRepository.save(cart);
                if(cart.getCartItems().isEmpty())
                    cartService.removeCart(cart.getId());
                return;
            }else {
                cartItem.setQuantity(quantity);
            }
        }else {
            cartItem = new CartItem(book,quantity);
        }
        addCartItem(cartItem);
    }
}
