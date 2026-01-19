package com.example.ecotory.domain.social.naver.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverTokenResponse {
    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("expires_in")
    int expiresIn;

    @JsonProperty("scope")
    String scope;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("id_token")
    String idToken;
}
