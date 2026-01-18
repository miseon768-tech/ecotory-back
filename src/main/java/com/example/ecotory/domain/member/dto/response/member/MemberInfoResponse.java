package com.example.ecotory.domain.member.dto.response.member;

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
}
