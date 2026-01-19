package com.example.ecotory.domain.social.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserInfo {
    private String provider;   // GOOGLE, NAVER, KAKAO
    private String providerId;
    private String email;
    private String nickname;
    private String picture;
}