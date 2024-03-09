package com.sparta.goods.domain.cart.service;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.dto.CartResponse;
import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.repository.CartItemRepository;
import com.sparta.goods.domain.cart.repository.CartRepository;
import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import com.sparta.goods.global.entity.User;
import com.sparta.goods.global.jwt.JwtUtil;
import com.sparta.goods.global.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final JwtUtil jwtUtil;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, JwtUtil jwtUtil) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.jwtUtil = jwtUtil;
    }

    public CartResponse addCartItem(CartRequest request, String tokenValue) {
        validateAndAuthenticateToken(tokenValue);

        Cart cart = getCart(tokenValue);

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new NullPointerException("Not Found")
        );

        cartItemRepository.save(new CartItem(cart, product, request.getCartQuantity()));

        return CartResponse.builder()
                .cartQuantity(request.getCartQuantity())
                .price(product.getPrice())
                .introduction(product.getIntroduction())
                .productName(product.getProductName())
                .category(product.getCategory())
                .build();
    }

    public CartResponse.CartResponseTotal getCart(Long cartId, String tokenValue) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NullPointerException("장바구니가 존재하지 않습니다.")
        );

        List<CartItem> cartItemList = cart.getItems();
        List<CartResponse> responses = new ArrayList<>();
        int totalPrice = 0;
        for (CartItem item : cartItemList) {
            responses.add(CartResponse.builder()
                    .cartQuantity(item.getQuantity())
                    .productName(item.getProduct().getProductName())
                    .introduction(item.getProduct().getIntroduction())
                    .category(item.getProduct().getCategory())
                    .price(item.getProduct().getPrice() * item.getQuantity())
                    .build()
            );
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        return CartResponse.CartResponseTotal.builder()
                .cartResponse(responses)
                .totalPrice(totalPrice)
                .build();
    }

    // 토큰 검증
    private void validateAndAuthenticateToken(String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }
    }

    public User authenticateUser(String tokenValue) {
        // 토큰에서 사용자 정보 추출
        Claims claims = jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(tokenValue));
        String email = claims.getSubject(); // "getSubject()"는 사용자 이름 또는 고유 식별자를 의미

        // 사용자 검증 및 반환
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("Not Found User"));

    }

    private Cart getCart(String tokenValue) {
        User user = authenticateUser(tokenValue);
        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            cartRepository.save(new Cart(user));
        }
        return cartRepository.findByUserId(user.getId())
                .orElse(null);
    }
}
