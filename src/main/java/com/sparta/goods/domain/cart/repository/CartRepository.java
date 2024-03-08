package com.sparta.goods.domain.cart.repository;

import com.sparta.goods.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
