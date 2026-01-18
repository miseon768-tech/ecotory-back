package com.example.ecotory.domain.member.service;

import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 정보 조회
    public Member memberInfo(String subject) {
        return memberRepository.findById(subject)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    }

    // 계정 정보 수정
    public void updateMember(String subject, String email, String nickname) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        member.setEmail(email);
        member.setNickname(nickname);

        memberRepository.save(member);
    }

    // 비밀번호 변경
    public void changePassword(String subject, String oldPassword, String newPassword, String newPasswordConfirm) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("새로운 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    // 회원 탈퇴
    public void deleteMember(String subject, String password) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
    }
}