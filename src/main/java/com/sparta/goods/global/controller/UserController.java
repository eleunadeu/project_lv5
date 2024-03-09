package com.sparta.goods.global.controller;

import com.sparta.goods.global.dto.LoginRequestDto;
import com.sparta.goods.global.dto.SignupRequestDto;
import com.sparta.goods.global.dto.UserResponseDto;
import com.sparta.goods.global.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "회원 가입, 로그인")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    ResponseEntity<Void> signupUser(@RequestBody SignupRequestDto requestDto) {

        userService.signupUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(requestDto, response));
    }
}
