package com.sparta.goods.global.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private String token;
    private String type = "Bearer";

    public UserResponseDto(String token) {
        this.token = token;
    }
}
