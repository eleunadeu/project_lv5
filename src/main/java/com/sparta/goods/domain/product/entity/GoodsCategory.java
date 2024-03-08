package com.sparta.goods.domain.product.entity;

import lombok.Getter;

@Getter
public enum GoodsCategory {
    DOLL("doll"), FANCY("fancy"), ANOTHER("another");
    private final String category;

    GoodsCategory(String category) {
        this.category = category;
    }
}
