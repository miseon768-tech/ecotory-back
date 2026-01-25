package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.emailCode.EmailCode;
import com.example.ecotory.domain.member.emailCode.EmailCodeService;
import com.example.ecotory.domain.member.dto.request.EmailRequest;
import com.example.ecotory.domain.member.dto.request.SignUpRequest;
import com.example.ecotory.domain.member.emailCode.dto.EmailCodeResponse;
import com.example.ecotory.domain.member.dto.response.SignUpResponse;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "") // 스웨거 문서에 보안 스킴 적용
@Tag(name = "", description = "")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class SignUpController {
    private final SignUpService signUpService;
    private final EmailCodeService emailCodeService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUpHandle(@RequestBody @Valid SignUpRequest request) {

        Member savedMember = signUpService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SignUpResponse.fromEntity(savedMember));
    }

    @Operation(summary = "이메일 인증 코드 전송", description = "사용자의 이메일로 인증 코드를 전송합니다.")
    @PostMapping("/email/code")
    public ResponseEntity<EmailCodeResponse> sendEmailCodeHandle(@RequestBody EmailRequest emailRequest) {

        EmailCode emailCode = emailCodeService.sendEmailCode(emailRequest.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EmailCodeResponse.fromEntity(emailCode));
    }
}
