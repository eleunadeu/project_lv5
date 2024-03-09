package com.sparta.goods.domain.cart.controller;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.dto.CartResponse;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.service.CartService;
import com.sparta.goods.global.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Secured("ROLE_USER")
    @PostMapping("/")
    public ResponseEntity<CartResponse> addCartItem(
            @RequestBody CartRequest request,
            @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addCartItem(request, tokenValue));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse.CartResponseTotal> getCart(
            @PathVariable Long cartId,
            @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.getCart(cartId, tokenValue));
    }
}
