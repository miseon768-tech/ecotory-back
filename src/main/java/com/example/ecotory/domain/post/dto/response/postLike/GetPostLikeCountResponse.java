package com.example.ecotory.domain.post.dto.response.postLike;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetPostLikeCountResponse {
    private Long likeCount;
    private boolean success;
}
