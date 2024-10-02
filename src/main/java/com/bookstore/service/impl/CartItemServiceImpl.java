package com.bookstore.service.impl;

import com.bookstore.dto.CartItemRequest;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.InvalidQuantityException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Book;
import com.bookstore.model.Cart;
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

import java.util.Optional;

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
    public CartItem findByCartAndBook(Cart cart, Book book) {
        return cartItemRepository.findByCartAndBook(cart,book);
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
        cartItemRepository.flush();
    }


    /**
     * Modify a book quantity in the cart for a given customer.
     * or add a new cartitem if the cart already exist
     *
     * @param cartItemRequest ->
     *  customerId the ID of the customer
     *  bookId the ID of the book to add
     *  quantity the number of books to add
     */
    @Override
    @Transactional
    public void modifyCartItem(CartItemRequest cartItemRequest){

        int quantity = cartItemRequest.getQuantity();
        log.debug("Quantity Requested  : {}", quantity);

        var cart = cartRepository.findByCustomerId(cartItemRequest.getCustomerId());
        log.debug("Cart fetched : {}", cart);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        var book = bookService.findById(cartItemRequest.getBookId());
        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst()
                .orElse(null);
        log.debug("CartItem : {}", cartItem);

        if ((quantity <= 0 && cartItem == null) || quantity < 0)
            throw new InvalidQuantityException();

        if (quantity > book.getStock()) {
            throw new OutOfStockException();
        }

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
            cartItem = new CartItem(book,quantity,cart);
        }
        addCartItem(cartItem);
    }
}
