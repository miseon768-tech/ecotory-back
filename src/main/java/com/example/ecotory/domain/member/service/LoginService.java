package com.example.ecotory.domain.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecotory.domain.member.dto.response.LoginResponse;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.dto.request.LoginRequest;
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

        String token = JWT.create()
                .withIssuer("ecotory")
                .withSubject(member.getId().toString())
                .sign(Algorithm.HMAC256("ecotoryKey"));

        return LoginResponse.fromEntity(member, token);
    }
}