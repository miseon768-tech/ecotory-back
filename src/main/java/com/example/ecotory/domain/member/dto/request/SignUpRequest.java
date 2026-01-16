package com.example.ecotory.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {

    @NotBlank
    @Email
    String email;

    // 대문자, 소문자, 숫자, 특수문자, 8-20자
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,20}")
    String password;

    @Size(min = 2, max = 6)
    String nickname;
}
