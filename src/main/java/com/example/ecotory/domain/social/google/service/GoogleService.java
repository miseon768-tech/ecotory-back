package com.example.ecotory.domain.social.google.service;

import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.social.entity.Social;
import com.example.ecotory.domain.social.google.response.GoogleTokenResponse;
import com.example.ecotory.domain.social.google.response.GoogleUserInfoResponse;
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
public class GoogleService {

    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    public GoogleTokenResponse exchangeToken(String code, String redirectUri) {

        RestClient restClient = RestClient.create();

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "authorization_code");
        body.put("code", code);
        body.put("redirect_uri", redirectUri);

        return restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .body(body)
                .retrieve()
                .body(GoogleTokenResponse.class);
    }

    // 구글 사용자 정보 가져오기
    public GoogleUserInfoResponse exchangeUserInfo(String accessToken) {
        RestClient restClient = RestClient.create();
        return restClient.get().uri("https://openidconnect.googleapis.com/v1/userinfo")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(GoogleUserInfoResponse.class);
    }


    // 3) DB 저장 로직 (조건 반영)
    public Member upsertGoogleMember(GoogleUserInfoResponse userInfo) {

        // 1. social 테이블에서 구글 계정이 있는지 먼저 확인
        Optional<Social> socialOptional =
                socialRepository.findByProviderAndProviderId("GOOGLE", userInfo.getSub());

        if (socialOptional.isPresent()) {
            // 이미 연결된 계정이면 해당 member 반환 (insert 없음)
            return socialOptional.get().getMember();
        }

        // 2. social이 없으면, 이메일 기준으로 member 찾기
        Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());

        Member member;
        if (memberOptional.isPresent()) {
            // 이메일이 같으면 같은 유저
            member = memberOptional.get();
        } else {
            // 신규 유저면 Member 생성
            member = memberRepository.save(Member.builder()
                    .email(userInfo.getEmail())
                    .nickname(userInfo.getName())
                    .build());
        }

        // 3. Social에 연결 정보 저장 (이메일이 같은 기존 사용자라면 여기서 social만 추가)
        socialRepository.save(Social.builder()
                .member(member)
                .provider("GOOGLE")
                .providerId(userInfo.getSub())
                .build());

        return member;
    }
}

