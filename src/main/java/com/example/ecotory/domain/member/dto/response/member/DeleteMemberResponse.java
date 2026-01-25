package com.example.ecotory.domain.member.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteMemberResponse {

    private boolean success;
    String message;

    public static DeleteMemberResponse fromEntity() {
        return DeleteMemberResponse.builder()
                .success(true)
                .message("회원 탈퇴가 완료되었습니다.")
                .build();
    }
}
