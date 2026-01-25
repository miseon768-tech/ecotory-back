package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetPostByKeywordResponse {
    private List<Post> postList;
    private boolean success;

    public static GetPostByKeywordResponse fromEntity(List<Post> response) {
        return GetPostByKeywordResponse.builder()
                .postList(response)
                .success(true)
                .build();
    }
}
