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
    public Member memberInfo(Member member) {
        return memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    }

    // 계정 정보 수정
    public void updateMember(Member member, String email, String nickname) {

        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        existingMember.setEmail(email);
        existingMember.setNickname(nickname);

        memberRepository.save(existingMember);
    }

    // 비밀번호 변경
    public void changePassword(Member member, String oldPassword, String newPassword, String newPasswordConfirm) {

        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(oldPassword, existingMember.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("새로운 비밀번호가 일치하지 않습니다.");
        }

        existingMember.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(existingMember);
    }

    // 회원 탈퇴
    public void deleteMember(Member member, String password) {

        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, existingMember.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(existingMember);
    }
}