package com.example.ecotory.domain.social.google.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GoogleUserInfoResponse {

    String sub;
    String name;
    String picture;
    String email;

}
