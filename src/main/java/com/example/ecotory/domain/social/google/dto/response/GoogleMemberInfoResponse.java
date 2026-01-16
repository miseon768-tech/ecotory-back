package com.example.ecotory.domain.social.google.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GoogleMemberInfoResponse {

    String sub;
    String name;
    String picture;
    String email;

}
