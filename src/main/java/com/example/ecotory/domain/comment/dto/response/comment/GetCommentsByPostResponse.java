package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetCommentsByPostResponse {
    private List<Comment> commentList;
    private boolean success;

    public static GetCommentsByPostResponse fromEntity(List<Comment> commentList, boolean success) {
        return GetCommentsByPostResponse.builder()
                .commentList(commentList)
                .success(success)
                .build();
    }
}
