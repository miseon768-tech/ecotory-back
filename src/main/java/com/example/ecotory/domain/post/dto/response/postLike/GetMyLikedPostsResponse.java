package com.example.ecotory.domain.post.dto.response.postLike;

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
}
