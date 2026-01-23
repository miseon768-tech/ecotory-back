package com.example.ecotory.domain.comment.controller;

import com.example.ecotory.domain.comment.dto.request.AddCommentRequest;
import com.example.ecotory.domain.comment.dto.request.UpdateCommentRequest;
import com.example.ecotory.domain.comment.dto.response.comment.*;
import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "특정 대상(글, 동영상 등)에 댓글을 작성합니다.")
    @PostMapping
    public ResponseEntity<?> addComment(@RequestAttribute String subject,
                                        @RequestBody String content)  {

        Comment comment = commentService.addComment(subject, content);

        AddCommentResponse response = AddCommentResponse.builder()
                .comment(comment)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable String commentId,
                                           @RequestAttribute String subject,
                                           @RequestBody UpdateCommentRequest updateCommentRequest)  {

        Comment comment = commentService.updateComment(commentId, subject, updateCommentRequest);

        UpdateCommentResponse response = UpdateCommentResponse.builder()
                .comment(comment)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @DeleteMapping({"/{commentId}"})
    public ResponseEntity<?> deleteComment(@PathVariable String commentId,
                                           @RequestAttribute String subject)  {

        DeleteCommentResponse response = commentService.deleteComment(commentId, subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "댓글 목록 조회", description = "특정 글의 댓글 목록을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<GetCommentsByPostResponse> getComments(@RequestAttribute String subject,
                                                                 @PathVariable String postId)  {

        GetCommentsByPostResponse response = commentService.getComments(subject, postId);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "사용자 댓글 목록 조회", description = "특정 사용자가 작성한 댓글 목록을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getCommentsByMember(@PathVariable String memberId)  {

        GetCommentsByMemberResponse response = commentService.getMyComments(memberId);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

}