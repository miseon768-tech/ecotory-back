package com.example.ecotory.domain.post.dto.response.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncreaseViewCountResponse {

    private boolean success;
    private int viewCount; // 증가 후 조회수


}