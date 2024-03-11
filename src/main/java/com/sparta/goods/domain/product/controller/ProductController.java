package com.sparta.goods.domain.product.controller;

import com.sparta.goods.domain.product.dto.ProductRequest;
import com.sparta.goods.domain.product.dto.ProductResponse;
import com.sparta.goods.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(name = "product controller", description = "상품 관련 컨트롤러")
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
    @Operation(summary = "상품(굿즈) 등록", description = "상품(굿즈) 이름, 가격, 수량, 설명, 카테고리를 등록할 수 있습니다.")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));

    }

    @GetMapping("/product/{id}")
    @Operation(summary = "선택한 상품(굿즈) 조회", description = "상품(굿즈) 등록 ID를 기준으로 상품(굿즈)를 조회하여 상품(굿즈) 정보를 확인할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "상품 조회 성공")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findProduct(id));

    }

    @GetMapping("/products")
    @Operation(summary = "상품(굿즈) 목록 조회", description = "등록된 상품(굿즈) 목록을 페이지로 조회할 수 있습니다, 상품(굿즈) 이름, 가격을 기준으로 오름차순 및 내림차순 정렬하여 조회할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(page - 1, size, sortBy, isAsc));
    }
}
