package com.sparta.goods.domain.cart.dto;

import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.global.entity.User;
import lombok.Getter;

@Getter
public class CartRequest {
    private Long productId;
    private int cartQuantity;

    public Cart toEntity(User user) {
        return new Cart(user);
    }
}
