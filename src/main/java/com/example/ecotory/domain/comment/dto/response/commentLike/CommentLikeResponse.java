package com.example.ecotory.domain.comment.dto.response.commentLike;

import com.example.ecotory.domain.comment.entity.CommentLike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentLikeResponse {
    private CommentLike commentLike;
    private boolean success;
}
