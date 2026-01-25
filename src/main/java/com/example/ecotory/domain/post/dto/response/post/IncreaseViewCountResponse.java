package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncreaseViewCountResponse {

    private boolean success;
    private int viewCount; // 증가 후 조회수


    public static IncreaseViewCountResponse fromEntity(Post post) {
        return IncreaseViewCountResponse.builder()
                .success(true)
                .viewCount(post.getViewCount())
                .build();
    }
}