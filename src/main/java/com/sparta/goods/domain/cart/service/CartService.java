package com.sparta.goods.domain.cart.service;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.dto.CartResponse;
import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.repository.CartRepository;
import com.sparta.goods.domain.cart.repository.CartItemRepository;

import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }
    // 수정
    @Transactional
    public CartResponse updateCartProduct(CartRequest requestDto) {
        Product product = productRepository.findByProductId(requestDto.getProductId()).orElseThrow(
                () -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));
        Cart cart = cartRepository.findByItemsProduct(product).orElseThrow(
                () -> new NoSuchElementException("장바구니에서 해당 상품을 찾을 수 없습니다."));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 장바구니에서 찾을 수 없습니다."));
        cartItem.updateCarProductQuantity(requestDto.getCartQuantity());
        return new CartResponse();
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
