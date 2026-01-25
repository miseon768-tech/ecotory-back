package com.example.ecotory.domain.member.dto.response.member;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordResponse {
    String newPassword;
    private boolean success;

    public static ChangePasswordResponse fromEntity(Member member) {
        return ChangePasswordResponse.builder()
                .newPassword(member.getPassword())
                .success(true)
                .build();
    }
}
