package com.bookstore.service;

import com.bookstore.util.TestUtils;
import com.bookstore.dto.CartItemRequest;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.InvalidQuantityException;
import com.bookstore.repo.CartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CartItemServiceTest {

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private BookService bookService;

    @Autowired
    private CartItemService cartItemService;

    @Test
    public void testModifyCartItem_CartNotFound_ThrowsException() {

        CartItemRequest request = TestUtils.generateCartItemRequest(1L,1L,5);
        when(cartRepository.findByCustomerId(request.getCustomerId())).thenReturn(null);
        assertThatThrownBy(()-> cartItemService.modifyCartItem(request)).isInstanceOf(CartNotFoundException.class);
    }

    @Test
    public void modifyCartItem_ShouldThrowInvalidQuantityException_WhenQuantityIsInvalid() {
        when(cartRepository.findByCustomerId(anyLong())).thenReturn(TestUtils.generateCartWithEmptyCartItem());
        when(bookService.findById(anyLong())).thenReturn(TestUtils.generateBookWithStock(-1));
        CartItemRequest request = TestUtils.generateCartItemRequest(1L,1L,-1);
        assertThatThrownBy(()-> cartItemService.modifyCartItem(request)).isInstanceOf(InvalidQuantityException.class);

    }

    @Test
    public void modifyCartItem_ShouldUpdateCartItemQuantity_WhenValidQuantity() {
        var cart = TestUtils.generateCartWithEmptyCartItem();
        var book = TestUtils.generateBookWithStock(10);
        var cartItem = TestUtils.generateCartItem(book, 2, cart);
        cart.getCartItems().add(cartItem);

        var cartItemRequest = TestUtils.generateCartItemRequest(1L, 1L, 5);

        when(cartRepository.findByCustomerId(anyLong())).thenReturn(cart);
        when(bookService.findById(anyLong())).thenReturn(TestUtils.generateBookWithStock(10));

        cartItemService.modifyCartItem(cartItemRequest);

        assertEquals(5, cartItem.getQuantity());
    }

}
