package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.emailCode.EmailCodeService;
import com.example.ecotory.domain.member.dto.request.EmailRequest;
import com.example.ecotory.domain.member.dto.request.SignUpRequest;
import com.example.ecotory.domain.member.emailCode.dto.EmailCodeResponse;
import com.example.ecotory.domain.member.dto.response.SignUpResponse;
import com.example.ecotory.domain.member.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> signUpHandle(@RequestBody @Valid SignUpRequest signUpRequest,
                                          BindingResult result) {

        // 입력 값 검증(이메일 형식, 비밀번호 길이 등)
        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body("유효하지 않은 입력입니다."); // 400 Bad Request
        }

        SignUpResponse response = signUpService.signUp(signUpRequest);

        // 이메일 중복
            if (!response.isSuccess()) {
                return ResponseEntity
                        .status(409)
                        .body("이미 존재하는 이메일입니다."); // 409 Conflict
            }

        // 회원가입 성공
            return ResponseEntity
                    .ok() // 200 OK
                    .body(response.getMember());
        }

    @Operation(summary = "이메일 인증 코드 전송", description = "사용자의 이메일로 인증 코드를 전송합니다.")
    @PostMapping("/email/code")
    public ResponseEntity<?> sendEmailCodeHandle(@RequestBody EmailRequest emailRequest) {

        EmailCodeResponse response = emailCodeService.sendEmailCode(emailRequest.getEmail());

        if (response.isSuccess()) {
            return ResponseEntity
                    .ok() // 200 OK
                    .body(response.getMessage());
        } else {
            return ResponseEntity
                    .badRequest() // 400 Bad Request
                    .body(response.getMessage());
        }
    }


}
