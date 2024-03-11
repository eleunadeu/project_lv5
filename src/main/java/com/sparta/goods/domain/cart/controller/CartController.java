package com.sparta.goods.domain.cart.controller;

import com.sparta.goods.domain.cart.dto.CartRequest;
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
    public ResponseEntity<String> updateCartProduct(@PathVariable Long productId, @RequestBody CartRequest requestDto) {
        // productId가 제공되고 유효한지 확인
        if (productId == null) {
            throw new IllegalArgumentException("제품 ID를 제공해야합니다.");
        }

        // 카트 서비스로 업데이트
        cartService.updateCartItemQuantity(new CartRequest(productId, requestDto.getCartQuantity()));

        // 수정 확인을 위해 OK 응답을 반환
        return ResponseEntity.ok("장바구니가 성공적으로 수정되었습니다.");
    }

    // 장바구니 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<String> deleteCartProduct(@PathVariable Long productId) {
        cartService.deleteCartProduct(productId);
        return ResponseEntity.ok("장바구니에서 해당 상품이 삭제되었습니다.");
    }
}
