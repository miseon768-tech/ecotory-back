package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeletePostResponse {
    private boolean success;

    public static DeletePostResponse fromEntity(Post post) {
        return DeletePostResponse.builder()
                .success(true)
                .build();
    }
}
