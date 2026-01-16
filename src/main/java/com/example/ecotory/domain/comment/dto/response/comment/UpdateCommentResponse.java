package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateCommentResponse {
    private Comment comment;
    private boolean success;
}
