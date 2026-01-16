//package com.example.ecotory.domain.social.google.service;
//
//import com.example.ecotory.domain.member.entity.Member;
//import com.example.ecotory.domain.social.google.dto.request.GoogleRequest;
//import com.example.ecotory.domain.social.google.dto.response.GoogleMemberInfoResponse;
//import com.example.ecotory.domain.social.google.dto.response.GoogleTokenResponse;
//import com.example.ecotory.domain.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class GoogleService {
//    final MemberRepository memberRepository;
//
//
//    // 구글 토큰 교환
//    public GoogleTokenResponse exchangeToken(String code, String redirectUri) {
//        RestClient restClient = RestClient.create();
//
//        Map<String, String> body = new HashMap<>();
//        body.put("client_id", "868269922654-hsmsl80i8qtc2n0au75bdee04u1thu7k.apps.googlemembercontent.com");
//        body.put("client_secret", "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");
//        body.put("grant_type", "authorization_code");
//        body.put("code", code);
//        body.put("redirect_uri", redirectUri);
//
//        return restClient.post()
//                .uri("https://oauth2.googleapis.com/token")
//                .body(body)
//                .retrieve()
//                .body(GoogleTokenResponse.class);
//    }
//
//    // 구글 사용자 정보 가져오기
//    public GoogleMemberInfoResponse exchangeMemberInfo(String accessToken) {
//        RestClient restClient = RestClient.create();
//        return restClient.get().uri("https://openidconnect.googleapis.com/v1/memberinfo")
//                .header("Authorization", "Bearer " + accessToken)
//                .retrieve()
//                .body(GoogleMemberInfoResponse.class);
//    }
//
//    // 구글 사용자 정보로 유저 정보 업데이트 또는 삽입
//    public Member upsertGoogleMember(GoogleMemberInfoResponse memberInfoResponse) {
//
//        // DTO 변환 (GooglememberInfoResponse → GoogleRequest)
//        GoogleRequest googleRequest = GoogleRequest.builder()
//                .provider("GOOGLE")
//                .providerId(memberInfoResponse.getSub())
//                .email(memberInfoResponse.getEmail())
//                .name(memberInfoResponse.getName())
//                .picture(memberInfoResponse.getPicture())
//                .build();
//
//        // 기존 유저 조회
//        Optional<Member> optionalmember = memberRepository.findByProviderAndProviderId(
//                googleRequest.getProvider(),
//                googleRequest.getProviderId()
//        );
//
//        return optionalmember.map(member -> {
//            // 기존 유저라면 정보 업데이트
//            member.setEmail(googleRequest.getEmail());
//            member.setName(googleRequest.getName());
//            member.setPicture(googleRequest.getPicture());
//            return memberRepository.save(member);
//        }).orElseGet(() -> {
//            // 신규 유저라면 insert
//            return memberRepository.save(com.example.ecotory.domain.member.entity.Member.builder()
//                    .provider(googleRequest.getProvider())
//                    .providerId(googleRequest.getProviderId())
//                    .email(googleRequest.getEmail())
//                    .name(googleRequest.getName())
//                    .picture(googleRequest.getPicture())
//                    .build());
//        });
//    }
//}
