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

    @Getter
    @NoArgsConstructor
    public static class CartResponseTotal {
        private int totalPrice;
        private List<CartResponse> cartItemList;

        @Builder
        public CartResponseTotal(int totalPrice, List<CartResponse> cartItemList) {
            this.totalPrice = totalPrice;
            this.cartItemList = cartItemList;
        }
    }
}
