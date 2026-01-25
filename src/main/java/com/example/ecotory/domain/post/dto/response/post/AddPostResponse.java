package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddPostResponse {
    private Post post;
    private boolean success;

    public static AddPostResponse fromEntity(Post post) {
        return AddPostResponse.builder()
                .post(post)
                .success(true)
                .build();
    }
}
