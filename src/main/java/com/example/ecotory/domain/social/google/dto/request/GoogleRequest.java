package com.example.ecotory.domain.social.google.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoogleRequest {
    private String provider;
    private String providerId;
    private String email;
    private String name;
    private String picture;
}