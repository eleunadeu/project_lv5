package com.sparta.goods.domain.cart.service;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.dto.CartResponse;
import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.repository.CartRepository;
import com.sparta.goods.domain.cart.repository.CartItemRepository;

import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import com.sparta.goods.global.entity.User;
import com.sparta.goods.global.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;



    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       CartItemRepository cartItemRepository, UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;

    }

    // 추가
    @Transactional
    public CartResponse addCartProduct(CartRequest request, UserDetails userDetails) {
        // 사용자 정보 조회
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 사용자의 장바구니 조회 또는 생성
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }

        // 상품 조회
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new NullPointerException("Not Found")
        );

        // 장바구니에 추가
        CartItem cartItem = cartItemRepository.findCartItemByProduct(product);
        if (cartItem == null) {
            cartItemRepository.save(new CartItem(cart, product, request.getCartQuantity()));
        } else {
            cartItem.increaseQuantity(request.getCartQuantity());
        }

        return CartResponse.builder()
                .cartQuantity(request.getCartQuantity())
                .price(product.getPrice())
                .introduction(product.getIntroduction())
                .productName(product.getProductName())
                .category(product.getCategory())
                .build();
    }

    // 조회
    public CartResponse.CartResponseTotal getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NullPointerException("장바구니가 존재하지 않습니다.")
        );

        List<CartItem> cartItemList = cart.getItems();
        List<CartResponse> responseList = new ArrayList<>();
        int totalPrice = 0;
        for (CartItem item : cartItemList) {
            responseList.add(CartResponse.builder()
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
                .cartItemList(responseList)
                .totalPrice(totalPrice)
                .build();
    }

    // 수정
    @Transactional
    public void updateCartItemQuantity(CartRequest requestDto) {
        // 상품 ID를 사용하여 상품 엔티티 찾기
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + requestDto.getProductId()));

        // 상품이 속한 장바구니 찾기
        Cart cart = cartRepository.findByItemsProduct(product)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 포함한 장바구니를 찾을 수 없습니다."));

        // 카트 찾기
        CartItem cartItem = findCartItemByProduct(cart, product);

        // 카트 항목이 존재하면 수량을 업데이트하고 변경 사항을 db에 저장
        if (cartItem != null) {
            // 카트 항목의 수량을 요청된 수량으로 업데이트
            cartItem.updateQuantity(requestDto.getCartQuantity());
            cartRepository.save(cart);
        } else {
            throw new EntityNotFoundException("장바구니에서 상품을 찾을 수 없습니다: " + requestDto.getProductId());
        }
    }

    // 상품을 나타내는 카트 항목을 찾는 메서드
    private CartItem findCartItemByProduct(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    // 삭제
    @Transactional
    public void deleteCartProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByProduct(product).orElseThrow(
                () -> new NoSuchElementException("장바구니 목록에 해당 상품을 찾을 수 없습니다."));

        cartItemRepository.delete(cartItem);
    }
}
