package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetMyPostResponse {
    private List<Post> postList;
    private boolean success;

    public static GetMyPostResponse fromEntity(List<Post> response) {
        return GetMyPostResponse.builder()
                .postList(response)
                .success(true)
                .build();
    }
}
