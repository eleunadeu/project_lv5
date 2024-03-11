package com.sparta.goods.domain.cart.repository;

import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProduct(Product product);
    CartItem findCartItemByProduct(Product product);
}
