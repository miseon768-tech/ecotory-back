package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import com.example.ecotory.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteCommentResponse {
    private Comment comment;
    private boolean success;

    public static DeleteCommentResponse fromEntity(Comment comment, boolean success) {
        return DeleteCommentResponse.builder()
                .comment(comment)
                .success(success)
                .build();
    }
}
