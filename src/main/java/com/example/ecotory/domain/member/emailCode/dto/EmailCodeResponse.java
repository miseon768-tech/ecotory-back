package com.example.ecotory.domain.member.emailCode.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCodeResponse {
    private boolean success;
    private String message;
}
