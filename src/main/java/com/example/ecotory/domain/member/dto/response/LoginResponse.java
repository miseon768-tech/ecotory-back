package com.example.ecotory.domain.member.dto.response;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class LoginResponse {
    boolean success;
    Member member;
    String token;

    public static LoginResponse fromEntity(Member member, String token) {
        return LoginResponse.builder()
                .success(true)
                .member(member)
                .token(token)
                .build();
    }
}
