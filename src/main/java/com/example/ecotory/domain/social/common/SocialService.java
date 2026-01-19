package com.example.ecotory.domain.social.common;


public interface SocialService {
    SocialUserInfo getUserInfo(String code, String state);
}