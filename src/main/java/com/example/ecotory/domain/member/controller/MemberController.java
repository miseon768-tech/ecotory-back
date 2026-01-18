package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.dto.response.member.ChangePasswordResponse;
import com.example.ecotory.domain.member.dto.response.member.DeleteMemberResponse;
import com.example.ecotory.domain.member.dto.response.member.MemberInfoResponse;
import com.example.ecotory.domain.member.dto.response.member.UpdateMemberResponse;
import com.example.ecotory.domain.member.entity.Member;

import com.example.ecotory.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> memberInfo(@RequestAttribute String subject) {

        Member getMyInfo = memberService.memberInfo(subject);

        MemberInfoResponse response = MemberInfoResponse.builder()
                .success(true)
                .id(getMyInfo.getId())
                .email(getMyInfo.getEmail())
                .nickname(getMyInfo.getNickname())
                .createdAt(String.valueOf(getMyInfo.getCreatedAt()))
                .build();

        return ResponseEntity.ok(response);
    }

    // 계정 정보 수정
    @PutMapping("/me")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestAttribute String subject,
                                                             @RequestBody String email,
                                                             @RequestBody String nickname) {

        memberService.updateMember(subject, email, nickname);

        UpdateMemberResponse response = UpdateMemberResponse.builder()
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // 비밀번호 변경
    @PutMapping("/me/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestAttribute String subject,
                                                                 @RequestBody String oldPassword,
                                                                 @RequestBody String newPassword,
                                                                 @RequestBody String newPasswordConfirm) {

        memberService.changePassword(subject, oldPassword, newPassword, newPasswordConfirm);

        ChangePasswordResponse response = ChangePasswordResponse.builder()
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@RequestAttribute String subject,
                                                             @RequestBody String password) {

        memberService.deleteMember(subject, password);

        DeleteMemberResponse response = DeleteMemberResponse.builder()
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}