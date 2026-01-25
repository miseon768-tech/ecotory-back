package com.example.ecotory.domain.comment.dto.request;

import com.example.ecotory.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCommentRequest {

    private String postId;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .postId(this.postId)
                .content(this.content)
                .build();
    }


}
