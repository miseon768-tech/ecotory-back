package com.example.ecotory.domain.member.emailCode.dto;

import com.example.ecotory.domain.member.emailCode.EmailCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCodeResponse {
    private String code;
    private String message;

    public static EmailCodeResponse fromEntity(EmailCode emailCode) {
        return EmailCodeResponse.builder()
                .code(emailCode.getCode())
                .message("인증 코드가 이메일로 전송되었습니다.")
                .build();
    }
}