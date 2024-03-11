package com.sparta.goods.global.service;

import com.sparta.goods.global.dto.LoginRequestDto;
import com.sparta.goods.global.dto.SignupRequestDto;
import com.sparta.goods.global.dto.UserResponseDto;
import com.sparta.goods.global.entity.User;
import com.sparta.goods.global.entity.UserRoleEnum;
import com.sparta.goods.global.jwt.JwtUtil;
import com.sparta.goods.global.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j(topic = "회원가입, 로그인 서비스 로직")
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN -> 환경변수 값으로 변경 예정
    private static final Map<String, UserRoleEnum> tokenRoleMap = Map.of(
            "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC", UserRoleEnum.ADMIN
    );

    // 비밀번호 검증 패턴
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_~]).{8,15}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public void signupUser(SignupRequestDto requestDto) {
        // 비밀번호 검증
        String password = requestDto.getPassword();
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 최소 8자리에서 최대 15자리이며, " +
                    "영어 대소문자(a~zA~Z), 숫자, 특수문자 !@#$%^&*()_~만 사용 가능합니다.");
        }
        // 비밀번호 암호화
        password = passwordEncoder.encode(requestDto.getPassword());

        // email 중복 확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 전화 번호 중복 확인
        String phoneNumber = requestDto.getPhoneNumber();
        Optional<User> checkPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (checkPhoneNumber.isPresent()) {
            throw new IllegalArgumentException("중복된 전화번호 입니다.");
        }

        // 관리자 여부 확인 및 권한 부여
        UserRoleEnum role = determineRoleByToken(requestDto.getAdminToken());

        User user = new User(requestDto.getEmail(), password, requestDto.getGender(),
                requestDto.getPhoneNumber(), requestDto.getAddress(), role);

        userRepository.save(user);
    }

    public UserResponseDto loginUser(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getEmail(), user.getRole());

        return new UserResponseDto(token);
    }

    // 관리자 여부 확인
    public UserRoleEnum determineRoleByToken(String adminToken) {
        UserRoleEnum role = tokenRoleMap.get(adminToken);
        if (role == null) {
            return UserRoleEnum.USER;
        }
        return role;
    }
}
