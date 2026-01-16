package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetCommentsByMemberResponse {
    private List<Comment> commentListByMember;
    private boolean success;
}
