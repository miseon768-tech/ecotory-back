package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.dto.request.LoginRequest;
import com.example.ecotory.domain.member.dto.response.LoginResponse;
import com.example.ecotory.domain.member.service.LoginService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Member", description = "회원 관련 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class LoginController {

    private final LoginService loginService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginHandle(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = loginService.login(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}