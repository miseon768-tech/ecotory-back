package com.example.ecotory.domain.member.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordResponse {
    private boolean success;
}
