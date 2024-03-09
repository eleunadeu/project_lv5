package com.sparta.goods.domain.cart.dto;

import com.sparta.goods.domain.product.entity.GoodsCategory;
import lombok.Getter;

@Getter
public class CartResponse {
    private String productName;
    private int price;
    private String introduction;
    private GoodsCategory category;
    private int cartQuantity;

}
