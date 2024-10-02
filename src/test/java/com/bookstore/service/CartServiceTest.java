package com.bookstore.service;

import com.bookstore.TestUtils;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
import com.bookstore.repo.CartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;
    Long customerId = 1L;
    Long bookId = 1L;
    int quantity = 5;

    @Test
    public void testAddBookToCart_whenStockNotSufficient(){

        when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(TestUtils.generateBookWithStock(3));
        assertThatThrownBy(() -> cartService.addBookToCart(customerId, bookId, quantity)).isInstanceOf(OutOfStockException.class);

    }

    @Test
    public void testAddBookToCart_whenNewCartIsCreated() {

        Customer customer = new Customer();
        customer.setId(customerId);

        when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(TestUtils.generateBookWithStock(10));
        when(customerService.findById(ArgumentMatchers.anyLong())).thenReturn(customer);
        when(cartRepository.findByCustomerId(ArgumentMatchers.anyLong())).thenReturn(null);

        cartService.addBookToCart(customerId, bookId, quantity);

        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartItemService, times(1)).addCartItem(any(CartItem.class));
    }

    @Test
    public void testAddBookToCart_whenCartItemAlreadyExists() {

        Customer customer = new Customer();
        customer.setId(customerId);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        var book = TestUtils.generateBookWithStock(10);

        CartItem cartItem = new CartItem(book, quantity, cart);

        when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(book);
        when(customerService.findById(ArgumentMatchers.anyLong())).thenReturn(customer);
        when(cartRepository.findByCustomerId(ArgumentMatchers.anyLong())).thenReturn(cart);
        when(cartItemService.findByCartAndBook(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(cartItem);

        cartService.addBookToCart(customerId, bookId, quantity);

        verify(cartItemService, times(1)).addCartItem(cartItem);
        verify(cartItemService, times(1)).findByCartAndBook(cart, book);
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    public void testAddBookToCart_whenNewCartItemCreated() {

        var book = TestUtils.generateBookWithStock(10);

        Customer customer = new Customer();
        customer.setId(customerId);

        Cart cart = new Cart();
        cart.setCustomer(customer);

        when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(book);
        when(customerService.findById(ArgumentMatchers.anyLong())).thenReturn(customer);
        when(cartRepository.findByCustomerId(ArgumentMatchers.anyLong())).thenReturn(cart);
        when(cartItemService.findByCartAndBook(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);
        cartService.addBookToCart(customerId, bookId, quantity);

        verify(cartItemService, times(1)).addCartItem(any(CartItem.class));
        verify(cartRepository, times(1)).save(cart);
    }
}
