package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.dto.request.ChangePasswordRequest;
import com.example.ecotory.domain.member.dto.request.UpdateMemberRequest;
import com.example.ecotory.domain.member.dto.response.member.ChangePasswordResponse;
import com.example.ecotory.domain.member.dto.response.member.DeleteMemberResponse;
import com.example.ecotory.domain.member.dto.response.member.MemberInfoResponse;
import com.example.ecotory.domain.member.dto.response.member.UpdateMemberResponse;
import com.example.ecotory.domain.member.entity.Member;

import com.example.ecotory.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> memberInfo(@RequestAttribute Member member) {

        Member getMyInfo = memberService.memberInfo(member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MemberInfoResponse.fromEntity(getMyInfo));
    }

    // 계정 정보 수정
    @PutMapping("/me")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestAttribute Member member,
                                                             @RequestBody UpdateMemberRequest request) {

        Member updateMember = memberService.updateMember(member, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UpdateMemberResponse.fromEntity(updateMember));
    }

    // 비밀번호 변경
    @PutMapping("/me/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestAttribute Member member,
                                                                 @RequestBody ChangePasswordRequest request) {

        Member changePassword = memberService.changePassword(member, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ChangePasswordResponse.fromEntity(changePassword));
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@RequestAttribute Member member,
                                             @RequestBody String password) {

        memberService.deleteMember(member, password);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(DeleteMemberResponse.fromEntity());
    }
}