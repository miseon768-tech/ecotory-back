package com.example.ecotory.domain.member.api;

import com.example.ecotory.domain.member.dto.response.ValidationResponse;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberRepository memberRepository;

    // 비밀번호 유효성 검사
    @GetMapping("/password/check")
    public ValidationResponse checkPassword(@RequestParam String password) {

        if (password.length() < 8 || password.length() > 20) {
            return new ValidationResponse(false, "비밀번호는 8자 이상, 20자 이하이어야 합니다.");
        }

        if (password.contains(" ")) {
            return new ValidationResponse(false, "비밀번호에 공백을 포함할 수 없습니다.");
        }

        return new ValidationResponse(true, "사용 가능한 비밀번호입니다.");
    }

    // 이메일 중복 확인
    @GetMapping("/email/check")
    public ValidationResponse checkEmail(@RequestParam String email) {

        if (memberRepository.existsByEmail(email)) {
            return new ValidationResponse(false, "이미 사용 중인 이메일입니다.");
        }

        return new ValidationResponse(true, "사용 가능한 이메일입니다.");
    }
}