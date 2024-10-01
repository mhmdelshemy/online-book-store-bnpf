package com.bookstore.api;

import com.bookstore.dto.CartDTO;
import com.bookstore.dto.CartItemRequest;
import com.bookstore.service.CartItemService;
import com.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cart/")
public class CartApi {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @PostMapping("addcart")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> addCart(@RequestBody CartItemRequest cartItemRequest) {
         cartService.addBookToCart(cartItemRequest.customerId(),cartItemRequest.bookId(),cartItemRequest.quantity());
        return ResponseEntity.ok("Book added to cart successfully!");
    }

    @PutMapping("modifycart")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> modifyCart(@RequestBody CartItemRequest cartItemRequest) {
        cartItemService.modifyCartItem(cartItemRequest);
        return ResponseEntity.ok("Book modified to cart successfully!");
    }

    @DeleteMapping("deletecart/{cartId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        cartService.removeCart(cartId);
        return ResponseEntity.ok("Cart Removed Successfully!");
    }

    @GetMapping("listcart/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CartDTO> listCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.findByCustomerId(customerId));
    }
}
