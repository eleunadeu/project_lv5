package com.sparta.goods.domain.cart.dto;

import com.sparta.goods.domain.product.entity.GoodsCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponse {
    private String productName;
    private int price;
    private String introduction;
    private GoodsCategory category;
    private int cartQuantity;

    @Builder
    public CartResponse(String productName, int price, String introduction, GoodsCategory category, int cartQuantity) {
        this.productName = productName;
        this.price = price;
        this.introduction = introduction;
        this.category = category;
        this.cartQuantity = cartQuantity;
    }
}
