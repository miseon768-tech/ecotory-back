/*
package com.example.ecotory.domain.social.google.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.social.google.dto.response.GoogleMemberInfoResponse;
import com.example.ecotory.domain.social.google.dto.response.GoogleResponse;
import com.example.ecotory.domain.social.google.dto.response.GoogleTokenResponse;
import com.example.ecotory.domain.social.google.service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/google")
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleService googleService;

    // Google OAuth2 인증 처리 핸들러
    @GetMapping
    public ResponseEntity<?> googleAuthHandle(@RequestParam String code,
                                              @RequestParam String redirectUri) {

        // 구글 토큰 교환
        GoogleTokenResponse tokenResponse = googleService.exchangeToken(code, redirectUri);

        // 사용자 정보 조회
        GoogleMemberInfoResponse memberInfoResponse = googleService.exchangeMemberInfo(tokenResponse.getAccessToken());

        // DB upsert
        Member member = googleService.upsertGoogleMember(memberInfoResponse);

        // JWT 토큰 생성
        String token = JWT.create()
                .withIssuer("ecotory")
                .withSubject(String.valueOf(member.getId()))
                .sign(Algorithm.HMAC256("092860be0db7b8fe"));

        // 응답 생성
        GoogleResponse googleResponse = GoogleResponse.builder()
                .member(member)
                .token(token)
                .build();

        return ResponseEntity.ok(googleResponse);
    }
}*/
