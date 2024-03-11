package com.sparta.goods.domain.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "상품(굿즈) 등록 ID")
    private Long productId;

    @Column
    @NotNull
    @Schema(description = "상품(굿즈) 이름", example = "르탄이 인형")
    private String productName;

    @Column
    @Min(100)
    @Schema(description = "상품(굿즈) 가격", example = "10000")
    private int price;

    @Column
    @Min(0)
    @Schema(description = "상품(굿즈) 수량", example = "10")
    private int quantity;

    @Column
    @NotNull
    @Schema(description = "상품(굿즈) 설명", example = "르탄이 인형입니다.")
    private String introduction;

    @Column
    @Enumerated(EnumType.STRING)
    @Schema(description = "상품(굿즈) 카테고리", example = "DOLL, FANCY, ANOTHER")
    private GoodsCategory category;


    @Builder
    public Product(String productName, int price, Integer quantity, String introduction, GoodsCategory category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.introduction = introduction;
        this.category = category;
    }
}
