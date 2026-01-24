package com.example.ecotory.domain.social.naver.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.social.naver.response.NaverResponse;
import com.example.ecotory.domain.social.naver.response.NaverTokenResponse;
import com.example.ecotory.domain.social.naver.response.NaverUserInfoResponse;
import com.example.ecotory.domain.social.naver.service.NaverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class NaverController {
    final NaverService naverService;

    // Naver OAuth2 인증 처리 핸들러
    @GetMapping("/naver")
    public ResponseEntity<?> naverAuthHandle(@RequestParam String code,
                                              @RequestParam String state) {

        // 1) code -> token
        NaverTokenResponse tokenResponse = naverService.exchangeToken(code, state);

        // 2) token -> user info
        NaverUserInfoResponse userInfo = naverService.exchangeUserInfo(tokenResponse.getAccessToken());

        // 3) DB 저장
        Member member = naverService.upsertNaverMember(userInfo);

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

        NaverResponse authResponse = NaverResponse.builder()
                .member(member)
                .token(jwt)
                .build();

        return ResponseEntity.ok(authResponse);
    }
}