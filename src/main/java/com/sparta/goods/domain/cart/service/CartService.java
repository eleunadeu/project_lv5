package com.sparta.goods.domain.cart.service;

import com.sparta.goods.domain.cart.dto.CartRequest;
import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.repository.CartRepository;
import com.sparta.goods.domain.cart.repository.CartItemRepository;

import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public void updateCartItemQuantity(CartRequest requestDto) {
        // 상품 ID를 사용하여 상품 엔티티를 찾습니다.
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 상품을 찾을 수 없습니다: " + requestDto.getProductId()));

        // 상품이 속한 장바구니를 찾습니다.
        Cart cart = cartRepository.findByItemsProduct(product)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 포함한 장바구니를 찾을 수 없습니다."));

        // 장바구니에서 상품을 나타내는 카트 항목을 찾습니다.
        CartItem cartItem = findCartItemByProduct(cart, product);

        // 만약 카트 항목이 존재하면, 수량을 업데이트하고 변경 사항을 데이터베이스에 저장합니다.
        if (cartItem != null) {
            // 카트 항목의 수량을 요청된 수량으로 업데이트합니다.
            cartItem.updateQuantity(requestDto.getCartQuantity());
            // 변경된 카트를 저장합니다.
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
