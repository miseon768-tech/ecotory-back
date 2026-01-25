package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetPostResponse {
    private Post post;
    private boolean success;

    public static GetPostResponse fromEntity(Post response) {
        return GetPostResponse.builder()
                .post(response)
                .success(true)
                .build();
    }
}
