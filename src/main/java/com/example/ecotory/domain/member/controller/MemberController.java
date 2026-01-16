package com.example.ecotory.domain.member.controller;

import com.example.ecotory.domain.member.dto.response.MemberResponse;
import com.example.ecotory.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Member", description = "회원 정보 관련 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {
    private final MemberService memberService;


    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<MemberResponse> findById(@RequestAttribute String subject) {


        MemberResponse response = memberService.findById(subject);


        return ResponseEntity
                .status(HttpStatus.CREATED) //201
                .body(response);
    }


    @Operation(summary = "계정 정보 수정", description = "로그인한 사용자의 계정 정보를 수정합니다.")
    @PostMapping
    public ResponseEntity<?> update(@RequestAttribute String subject) {
        return null;
    }

}
