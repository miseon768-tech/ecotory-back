package com.example.ecotory.domain.social.google.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.social.google.response.GoogleResponse;
import com.example.ecotory.domain.social.google.service.GoogleService;
import com.example.ecotory.domain.social.google.response.GoogleTokenResponse;
import com.example.ecotory.domain.social.google.response.GoogleUserInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class GoogleController {
    final GoogleService googleService;
    final MemberRepository memberRepository;

    // Google OAuth2 인증 처리 핸들러
    @GetMapping("/google")
    public ResponseEntity<?> googleAuthHandle(@RequestParam String code,
                                              @RequestParam String redirectUri) {

        // 1) code -> token
        GoogleTokenResponse tokenResponse = googleService.exchangeToken(code, redirectUri);

        // 2) token -> user info
        GoogleUserInfoResponse userInfo = googleService.exchangeUserInfo(tokenResponse.getAccessToken());

        // 3) DB 저장 (기존 회원이면 insert 없음)
        Member member = googleService.upsertGoogleMember(userInfo);

        // 4) JWT 발급 (subject에 Member JSON 저장)
        ObjectMapper objectMapper = new ObjectMapper();
        String memberJson;
        try {
            memberJson = objectMapper.writeValueAsString(member);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("토큰 생성 중 사용자 직렬화 실패", e);
        }

        String jwt = JWT.create()
                .withIssuer("ecotory")
                .withSubject(memberJson)
                .sign(Algorithm.HMAC256("ecotoryKey"));

        GoogleResponse authResponse = GoogleResponse.builder()
                .member(member)
                .token(jwt)
                .build();

        return ResponseEntity.ok(authResponse);
    }
}