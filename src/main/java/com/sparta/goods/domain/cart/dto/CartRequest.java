package com.sparta.goods.domain.cart.dto;

import lombok.Getter;

@Getter
public class CartRequest {
    private Long productId;
    private int cartQuantity;
    public CartRequest() {}

    public CartRequest(Long productId, int cartQuantity) {
        this.productId = productId;
        this.cartQuantity = cartQuantity;
    }
}
