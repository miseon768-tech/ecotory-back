package com.example.ecotory.domain.social.google;

import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;

import com.example.ecotory.domain.social.common.Social;
import com.example.ecotory.domain.social.common.SocialRepository;
import com.example.ecotory.domain.social.common.SocialService;
import com.example.ecotory.domain.social.common.SocialUserInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleService implements SocialService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SocialRepository socialRepository;
    private final MemberRepository memberRepository;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Override
    public SocialUserInfo getUserInfo(String code, String state) {
        // 1. access token 받기
        String tokenUrl = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "authorization_code");
        body.put("redirect_uri", redirectUri);
        body.put("code", code);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        JsonNode tokenNode = objectMapper.readTree(response.getBody());
        String accessToken = tokenNode.get("access_token").asText();

        // 2. user info 받기
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);

        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<String> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, String.class);

        // 3. user info 파싱
        JsonNode userNode = objectMapper.readTree(userResponse.getBody());

        String providerId = userNode.get("id").asText();
        String email = userNode.get("email").asText();
        String name = userNode.get("name").asText();
        String picture = userNode.get("picture").asText();

        return SocialUserInfo.builder()
                .provider("GOOGLE")
                .providerId(providerId)
                .email(email)
                .name(name)
                .picture(picture)
                .build();
    }

    // 구글 사용자 정보로 멤버 정보 업데이트 또는 삽입
    public Member upsertGoogleMember(SocialUserInfo info) {

        Optional<Social> optionalSocial =
                socialRepository.findByProviderAndProviderId("GOOGLE", info.getProviderId());

        if (optionalSocial.isPresent()) {
            return optionalSocial.get().getMember();
        }

        // 이메일로 기존 회원 찾기
        Optional<Member> optionalMember = memberRepository.findByEmail(info.getEmail());

        Member member = optionalMember.orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(info.getEmail())
                    .nickname(info.getNickname())
                    .build();
            return memberRepository.save(newMember);
        });

        // Social 저장
        Social social = Social.builder()
                .provider("GOOGLE")
                .providerId(info.getProviderId())
                .email(info.getEmail())
                .nickname(info.getNickname())
                .picture(info.getPicture())
                .member(member)
                .build();

        socialRepository.save(social);
        return member;
    }
}