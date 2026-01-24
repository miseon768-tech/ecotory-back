package com.example.ecotory.domain.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.dto.request.LoginRequest;
import com.example.ecotory.domain.member.dto.response.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;


    // 로그인
    public LoginResponse login(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 일치하지 않습니다."));

        if (!loginRequest.getPassword().equals(member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성 (subject에 Member를 JSON으로 담음)
        ObjectMapper objectMapper = new ObjectMapper();
        String memberJson;
        try {
            memberJson = objectMapper.writeValueAsString(member);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("토큰 생성 중 사용자 직렬화 실패", e);
        }

        String token = JWT.create()
                .withIssuer("ecotory")
                .withSubject(memberJson)
                .sign(Algorithm.HMAC256("ecotoryKey"));

        return LoginResponse.builder()
                .success(true)
                .member(member)
                .token(token)
                .build();
    }

}