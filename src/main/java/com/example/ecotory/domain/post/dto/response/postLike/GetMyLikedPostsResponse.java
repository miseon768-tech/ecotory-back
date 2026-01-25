package com.example.ecotory.domain.post.dto.response.postLike;

import com.example.ecotory.domain.post.entity.PostLike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetMyLikedPostsResponse {
    private List<String> postList;
    private boolean success;

    public static GetMyLikedPostsResponse fromEntity(List<String> response) {
        return GetMyLikedPostsResponse.builder()
                .postList(response)
                .success(true)
                .build();
    }
}
