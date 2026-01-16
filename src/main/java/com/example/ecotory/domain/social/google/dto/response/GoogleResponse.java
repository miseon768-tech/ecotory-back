package com.example.ecotory.domain.social.google.dto.response;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GoogleResponse {
    String token;
    Member member;
}
