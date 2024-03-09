package com.sparta.goods;

import com.sparta.goods.domain.cart.entity.Cart;
import com.sparta.goods.domain.cart.entity.CartItem;
import com.sparta.goods.domain.cart.repository.CartItemRepository;
import com.sparta.goods.domain.cart.repository.CartRepository;
import com.sparta.goods.domain.product.entity.Product;
import com.sparta.goods.domain.product.repository.ProductRepository;
import com.sparta.goods.global.entity.User;
import com.sparta.goods.global.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Rollback(value = false)
    @DisplayName("장바구니 추가")
    void test1() {
        Long productId = 1L;
        int cartQuantity = 3;

        Cart cart = getCart();

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("Not Found")
        );

        cartItemRepository.save(new CartItem(cart, product, cartQuantity));
    }

    private Cart getCart() {
        User user = new User(
                "a123@email.com",
                passwordEncoder.encode("abc123ABC!@"),
                "남자",
                "01012341234",
                "서울시",
                UserRoleEnum.USER);

        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            cartRepository.save(new Cart(user));
        }
        return cartRepository.findByUserId(user.getId())
                .orElse(null);
    }
}
