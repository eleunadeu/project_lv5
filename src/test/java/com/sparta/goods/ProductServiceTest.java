package com.sparta.goods;

import com.sparta.goods.domain.product.repository.ProductRepository;
import com.sparta.goods.domain.product.service.ProductService;
import com.sparta.goods.global.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("상품 등록 테스트")
    void createProductTest() {
        //Given

        //When

        //Then
    }
}
