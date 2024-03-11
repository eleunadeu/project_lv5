package com.sparta.goods.global.controller;

import com.sparta.goods.global.dto.LoginRequestDto;
import com.sparta.goods.global.dto.SignupRequestDto;
import com.sparta.goods.global.dto.UserResponseDto;
import com.sparta.goods.global.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "회원 가입", description = "이메일(아이디), 비밀번호, 성별, 전화번호, 주소, 관리자 여부를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "회원 가입 완료")
    ResponseEntity<Void> signupUser(@RequestBody SignupRequestDto requestDto) {

        userService.signupUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "로그인 완료")
    @Operation(summary = "로그인", description = "회원 이메일(아이디), 비밀번호를 입력해 로그인할 수 있습니다.")
    ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(requestDto));
    }
}
