package com.example.ecotory.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
    private boolean success;

    public static ChangePasswordRequest fromEntity(String oldPassword, String newPassword, String newPasswordConfirm) {
        return ChangePasswordRequest.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .newPasswordConfirm(newPasswordConfirm)
                .success(true)
                .build();
    }
}
