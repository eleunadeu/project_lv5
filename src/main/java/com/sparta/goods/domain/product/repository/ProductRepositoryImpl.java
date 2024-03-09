package com.sparta.goods.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findProductsUsingQueryDsl(int page, int size, String sortBy, boolean isAsc) {
        QProduct product = QProduct.product;
        Order order = isAsc ? Order.ASC : Order.DESC;
        OrderSpecifier<?> orderSpecifier = switch (sortBy) {
            case "name" -> new OrderSpecifier<>(order, product.productName);
            case "price" -> new OrderSpecifier<>(order, product.price);
            // 다른 필드에 대한 case를 추가합니다.
            default -> throw new IllegalArgumentException("정렬을 위한 유효하지 않은 필드입니다: " + sortBy);
        };

        List<Product> products = queryFactory
                .selectFrom(product)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();

        // fetchCount()의 deprecated된 경고를 해결하기 위해
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .fetchOne();

        // null 체크를 통해 NullPointerException 방지
        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(products, PageRequest.of(page, size), totalCount);
    }
}
