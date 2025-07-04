package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;
import com.dailycodework.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart( @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);
//            if (cartId == null) {
//                cartId = cartService.initializeNewCart();
//            }
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add item to cart success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove item success", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update item success", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
