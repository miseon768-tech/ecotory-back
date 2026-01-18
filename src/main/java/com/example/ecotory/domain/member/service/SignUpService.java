package com.example.ecotory.domain.member.service;

import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.dto.request.SignUpRequest;
import com.example.ecotory.domain.member.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    final MemberRepository memberRepository;
    final PasswordEncoder passwordEncoder;

    // 회원가입
    public SignUpResponse signUp(SignUpRequest signUpRequest) {

        // 1. 이메일 중복 체크
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            return SignUpResponse.builder()
                    .success(false)
                    .member(null)
                    .build();
        }

        // 2. 신규 유저 생성 및 저장

        String encoded = passwordEncoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(encoded)
                .nickname(signUpRequest.getNickname())
                .build();

        Member savedMember = memberRepository.save(member);

        return SignUpResponse.builder()
                .success(true)
                .member(savedMember)
                .build();
    }
}
