package com.example.ecotory.domain.post.dto.response.postLike;

import com.example.ecotory.domain.post.entity.PostLike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetPostLikeCountResponse {
    private Long likeCount;
    private boolean success;

    public static GetPostLikeCountResponse fromEntity(Long likeCount) {
        return GetPostLikeCountResponse.builder()
                .likeCount(likeCount)
                .success(true)
                .build();
    }
}
