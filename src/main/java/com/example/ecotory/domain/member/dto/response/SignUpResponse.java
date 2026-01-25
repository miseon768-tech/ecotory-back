package com.example.ecotory.domain.member.dto.response;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignUpResponse {
    boolean success;
    Member member;

    public static SignUpResponse fromEntity(Member member) {
        return SignUpResponse.builder()
                .member(member)
                .success(true)
                .build();
    }
}
