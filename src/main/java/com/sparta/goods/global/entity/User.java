package com.sparta.goods.global.entity;

import com.sparta.goods.domain.cart.entity.Cart;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "유저 생성 ID")
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    @Schema(description = "로그인 시 ID", example = "sparta@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "비밀번호", example = "abcd1234@")
    private String password;

    @Column(nullable = false)
    @Schema(description = "사용자 성별", example = "남자, 여자")
    private String gender;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Column(nullable = false)
    @Schema(description = "사용자 주소", example = "서울시")
    private String address;

    @Column(nullable = false)
    @Schema(description = "사용자 등급", example = "USER, ADMIN")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(description = "카트 정보")
    private Cart cart;

    public User(String email, String password, String gender,
                String phoneNumber, String address, UserRoleEnum role) {

        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
}
