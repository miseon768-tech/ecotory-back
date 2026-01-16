package com.example.ecotory.domain.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCommentRequest {

    private String postId;
    private String content;



}
