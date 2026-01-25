package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PinPostResponse {

    private boolean success;
    private boolean pinned; // 현재 상단 고정 상태

    public static PinPostResponse fromEntity(Post post) {
        return PinPostResponse.builder()
                .success(true)
                .pinned(post.isPinned())
                .build();
    }
}