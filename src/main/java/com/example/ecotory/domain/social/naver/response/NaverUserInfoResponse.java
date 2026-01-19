package com.example.ecotory.domain.social.naver.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverUserInfoResponse {

    String sub;
    String nickname;
    String picture;
    String email;

}
