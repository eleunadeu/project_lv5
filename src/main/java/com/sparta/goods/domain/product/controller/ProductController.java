package com.sparta.goods.domain.product.controller;

import com.sparta.goods.domain.product.dto.ProductRequest;
import com.sparta.goods.domain.product.dto.ProductResponse;
import com.sparta.goods.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "상품 등록 및 조회")
@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findProduct(id));

    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(page - 1, size, sortBy, isAsc));
    }
}
