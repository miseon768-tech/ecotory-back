package com.example.ecotory.domain.social.naver;

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
public class NaverService implements SocialService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SocialRepository socialRepository;
    private final MemberRepository memberRepository;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    // 1. 네이버 사용자 정보 가져오기
    @Override
    public SocialUserInfo getUserInfo(String code, String state) {
        // 1. access token 받기
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "authorization_code");
        body.put("redirect_uri", redirectUri);
        body.put("code", code);
        body.put("state", state);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        JsonNode tokenNode = objectMapper.readTree(response.getBody());
        String accessToken = tokenNode.get("access_token").asText();

        // 2. 사용자 정보 요청
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);


        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<String> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, String.class);

        // 3. 사용자 정보 파싱
        JsonNode userNode = objectMapper.readTree(userResponse.getBody()).get("response");

        String providerId = userNode.get("id").asText();
        String email = userNode.get("email").asText();
        String name = userNode.get("name").asText();
        String picture = userNode.get("profile_image").asText();

        return SocialUserInfo.builder()
                .provider("NAVER")
                .providerId(providerId)
                .email(email)
                .name(name)
                .picture(picture)
                .build();
    }

    // 2. 네이버 사용자 정보로 멤버 정보 업데이트 또는 삽입
    public Member upsertNaverMember(SocialUserInfo info) {

        Optional<Social> optionalSocial =
                socialRepository.findByProviderAndProviderId("NAVER", info.getProviderId());

        if (optionalSocial.isPresent()) {
            return optionalSocial.get().getMember();
        }

        // 현재 네이버를 통해 접근한 사용자가 우리 서비스를 처음 접근하는거라면
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
                .provider("NAVER")
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