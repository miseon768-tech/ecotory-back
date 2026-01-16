package com.example.ecotory.domain.post.dto.response.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PinPostResponse {

    private boolean success;
    private boolean pinned; // 현재 상단 고정 상태

}