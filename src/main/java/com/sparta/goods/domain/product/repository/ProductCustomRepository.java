package com.sparta.goods.domain.product.repository;

import com.sparta.goods.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCustomRepository {
    Page<Product> findProductsUsingQueryDsl(int page, int size, String sortBy, boolean isAsc);
}
