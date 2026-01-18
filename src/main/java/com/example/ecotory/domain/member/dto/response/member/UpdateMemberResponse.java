package com.example.ecotory.domain.member.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberResponse {
    private boolean success;

    public UpdateMemberResponse(boolean success) {
        this.success = success;
    }
}
