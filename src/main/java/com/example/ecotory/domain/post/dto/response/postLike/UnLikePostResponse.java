package com.example.ecotory.domain.post.dto.response.postLike;

import com.example.ecotory.domain.post.entity.PostLike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnLikePostResponse {
    private PostLike postLike;
    private boolean success;

    public static UnLikePostResponse fromEntity(PostLike response) {
        return UnLikePostResponse.builder()
                .postLike(response)
                .success(true)
                .build();
    }
}
