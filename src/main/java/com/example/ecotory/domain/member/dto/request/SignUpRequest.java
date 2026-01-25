package com.example.ecotory.domain.member.dto.request;

import com.example.ecotory.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignUpRequest {

    @NotBlank
    @Email
    private String email;

    // 대문자, 소문자, 숫자, 특수문자, 8-20자
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,20}")
    private String password;

    @Size(min = 2, max = 6)
    private String nickname;

    // Member 엔티티로 변환
    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .build();
    }
}