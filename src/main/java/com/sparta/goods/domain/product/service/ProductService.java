package com.sparta.goods.domain.product.service;

import com.sparta.goods.domain.product.dto.ProductRequest;
import com.sparta.goods.domain.product.dto.ProductResponse;
import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        try {
            Product product = new Product(request.getProductName(), request.getPrice(), request.getQuantity(),
                    request.getIntroduction(), request.getCategory());

            productRepository.save(product);

            return new ProductResponse(product.getProductId(), product.getProductName(), product.getPrice(),
                    product.getQuantity(), product.getIntroduction(), product.getCategory());
        } catch (DataAccessException e) {
            // 데이터베이스 접근 중 발생하는 예외 처리
            throw new ServiceException("데이터베이스 처리 중 에러가 발생했습니다.", e);
        } catch (Exception e) {
            // 기타 예상치 못한 예외 처리
            throw new ServiceException("상품 생성 중 예상치 못한 에러가 발생했습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(Long id) {
        // ID를 사용하여 상품 조회
        Optional<Product> productOptional = productRepository.findById(id);

        // 조회된 상품이 존재하는 경우, ProductResponse 객체 생성 및 반환
        return productOptional.map(product -> new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getIntroduction(),
                product.getCategory()
        )).orElseThrow(() -> new EntityNotFoundException("상품 아이디: " + id + "에 해당되는 상품이 없습니다"));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product -> new ProductResponse(product.getProductId(), product.getProductName(),
                product.getPrice(), product.getQuantity(), product.getIntroduction(), product.getCategory()));

    }
}
