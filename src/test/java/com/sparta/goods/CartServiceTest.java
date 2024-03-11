//package com.sparta.goods;
//
//import com.sparta.goods.domain.cart.entity.CartItem;
//import com.sparta.goods.domain.cart.repository.CartItemRepository;
//import com.sparta.goods.domain.cart.repository.CartRepository;
//import com.sparta.goods.domain.cart.service.CartService;
//import com.sparta.goods.domain.product.entity.Product;
//import com.sparta.goods.domain.product.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.util.Optional;
//import static org.mockito.Mockito.*;
//import static org.mockito.internal.verification.VerificationModeFactory.times;
//
//@SpringBootTest
//public class CartServiceTest {
//
//    @Mock
//    private CartItemRepository cartItemRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private CartService cartService;
//
//    public CartServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testDeleteCartProduct_Success() {
//        // Given
//        Long productId = 1L;
//        Product product = mock(Product.class); // Create a mock Product
//        CartItem cartItem = mock(CartItem.class); // Create a mock CartItem
//
//        // Mocking the behavior of ProductRepository
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // Mocking the behavior of CartItemRepository
//        when(cartItemRepository.findByProduct(product)).thenReturn(Optional.of(cartItem));
//
//        // When
//        cartService.deleteCartProduct(productId);
//
//        // Then
//        verify(cartItemRepository, times(1)).findByProduct(product);
//        verify(cartItemRepository, times(1)).delete(cartItem);
//
//        // Verify the order of method invocations
//        InOrder inOrder = inOrder(cartItemRepository);
//        inOrder.verify(cartItemRepository).findByProduct(product);
//        inOrder.verify(cartItemRepository).delete(cartItem);
//    }
//    @Test
//    public void testDeleteCartProduct_ProductNotFound() {
//        // Given
//        Long productId = 1L;
//
//        // Mocking the behavior of ProductRepository
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // When
//        cartService.deleteCartProduct(productId);
//
//        // Then
//        verify(cartItemRepository, never()).findByProduct(any());
//        verify(cartItemRepository, never()).delete(any());
//    }
//
//    @Test
//    public void testDeleteCartProduct_CartItemNotFound() {
//        // Given
//        Long productId = 1L;
//        Product product = mock(Product.class); // Create a mock Product
//
//        // Mocking the behavior of ProductRepository
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        // Mocking the behavior of CartItemRepository
//        when(cartItemRepository.findByProduct(product)).thenReturn(Optional.empty());
//
//        // When
//        cartService.deleteCartProduct(productId);
//
//        // Then
//        verify(cartItemRepository, times(1)).findByProduct(product);
//        verify(cartItemRepository, never()).delete(any());
//    }
//}