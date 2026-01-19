package com.example.ecotory.domain.social.naver.service;

import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.social.entity.Social;
import com.example.ecotory.domain.social.naver.response.NaverTokenResponse;
import com.example.ecotory.domain.social.naver.response.NaverUserInfoResponse;
import com.example.ecotory.domain.social.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;


    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    public NaverTokenResponse exchangeToken(String code, String state) {

        RestClient restClient = RestClient.create();

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "authorization_code");
        body.put("code", code);
        body.put("state", state);

        return restClient.post()
                .uri("https://nid.naver.com/oauth2.0/token")
                .body(body)
                .retrieve()
                .body(NaverTokenResponse.class);
    }

    // 네이버 사용자 정보 가져오기
    public NaverUserInfoResponse exchangeUserInfo(String accessToken) {
        RestClient restClient = RestClient.create();
        return restClient.get().uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(NaverUserInfoResponse.class);
    }

    // 3) DB 저장 로직
    public Member upsertNaverMember(NaverUserInfoResponse userInfo) {

        // 1) Social 먼저 확인
        Optional<Social> socialOptional =
                socialRepository.findByProviderAndProviderId("NAVER", userInfo.getSub());

        if (socialOptional.isPresent()) {
            return socialOptional.get().getMember();
        }

        // 2) Social 없으면 이메일로 Member 조회
        Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());

        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
        } else {
            member = memberRepository.save(Member.builder()
                    .email(userInfo.getEmail())
                    .nickname(userInfo.getNickname())
                    .build());
        }

        // 3) Social 저장
        socialRepository.save(Social.builder()
                .member(member)
                .provider("NAVER")
                .providerId(userInfo.getSub())
                .build());

        return member;
    }
}