package com.sparta.goods.domain.cart.controller;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.dto.CartResponse;
import com.sparta.goods.domain.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 수정
    // @PreAuthorize("hasRole('USER')")
    // USER 사용자(인증된 사용자)만 액세스 가능
    // Spring Security 구성의 JWT 인증 설정과 결합되어
    // 인증된 사용자만 장바구니를 편집할 수 있고
    // 로그인을 통해 발행된 JWT 토큰이 필요하다는 요구 사항을 적용
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart/{productId}")
    public CartResponse updateCartProduct(@RequestBody CartRequest requestDto) {
        return cartService.updateCartProduct(requestDto);
    }

    // 장바구니 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<String> deleteCartProduct(@PathVariable Long productId) {
        cartService.deleteCartProduct(productId);
        return ResponseEntity.ok("장바구니에서 해당 상품이 삭제되었습니다.");
    }
}
