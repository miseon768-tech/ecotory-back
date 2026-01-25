package com.example.ecotory.domain.member.dto.response.member;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponse {
    private String id;
    private String email;
    private String nickname;
    private String createdAt;
    private Boolean success;

    public static MemberInfoResponse fromEntity(Member member) {
        return MemberInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createdAt(String.valueOf(member.getCreatedAt()))
                .success(true)
                .build();
    }
}
