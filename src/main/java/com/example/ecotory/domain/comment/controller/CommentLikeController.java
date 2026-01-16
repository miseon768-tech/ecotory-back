package com.example.ecotory.domain.comment.controller;

import com.example.ecotory.domain.comment.dto.response.commentLike.CommentLikeCancelResponse;
import com.example.ecotory.domain.comment.dto.response.commentLike.CommentLikeResponse;
import com.example.ecotory.domain.comment.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Comment Like", description = "댓글 좋아요 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/comment/like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 누릅니다.")
    @PostMapping("/{commentId}")
    public ResponseEntity<CommentLikeResponse> likeComment(@PathVariable String commentId,
                                                           @RequestAttribute String subject) {

        CommentLikeResponse response = commentLikeService.createCommentLike(commentId, subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "댓글 좋아요 취소", description = "특정 댓글에 대한 좋아요를 취소합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentLikeCancelResponse> unlikeComment(@PathVariable String commentId,
                                                                   @RequestAttribute String subject) {

        CommentLikeCancelResponse response = commentLikeService.deleteCommentLike(commentId, subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


}