package com.sparta.goods.global.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private String gender;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;

    private boolean admin = false;
    @NotNull
    private String adminToken;

    public SignupRequestDto(String email, String password, String gender,
                            String phoneNumber, String address, String adminToken) {

        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.adminToken = adminToken;
    }
}
