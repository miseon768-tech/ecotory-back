package com.example.ecotory.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResponse {
    private boolean valid;
    private String message;
}