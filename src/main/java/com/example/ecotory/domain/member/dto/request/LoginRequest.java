package com.example.ecotory.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    String email;
    String password;
}
