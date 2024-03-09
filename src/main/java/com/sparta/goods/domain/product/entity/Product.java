package com.sparta.goods.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column
    @NotNull
    private String productName;

    @Column
    @Min(100)
    private Integer price;

    @Column
    @Min(0)
    private Integer quantity;

    @Column
    @NotNull
    private String introduction;

    @Column
    @Enumerated(EnumType.STRING)
    private GoodsCategory category;


    @Builder
    public Product(String productName, Integer price, Integer quantity, String introduction, GoodsCategory category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.introduction = introduction;
        this.category = category;
    }
}
