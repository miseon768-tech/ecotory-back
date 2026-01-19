package com.example.ecotory.domain.social.naver.response;

import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NaverResponse {
    String token;
    Member member;
}
