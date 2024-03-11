package com.sparta.goods.domain.cart.repository;

import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
    Optional<Cart> findByItemsProduct(Product product);
}
