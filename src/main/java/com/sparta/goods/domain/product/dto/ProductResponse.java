package com.sparta.goods.domain.product.dto;

import com.sparta.goods.domain.product.entity.GoodsCategory;
import com.sparta.goods.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {

    private Long productId;
    private String productName;
    private int price;
    private Integer quantity;
    private String introduction;
    private GoodsCategory category;

    @Builder
    public ProductResponse(Long productId, String productName, int price, Integer quantity,
                           String introduction, GoodsCategory category) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.introduction = introduction;
        this.category = category;
    }
}
