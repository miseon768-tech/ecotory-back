package com.example.ecotory.domain.member.service;

import com.example.ecotory.domain.member.dto.response.MemberResponse;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;

    // 내 정보 조회
    public MemberResponse findById(String subject) {
        return null;
    }

    // 계정 정보 수정
    public void deleteMemberById(String subject) {
        return;
    }



}



/*
    비밀번호 변경
    로그인 사용자 정보 조회
    회원 탈퇴*/

