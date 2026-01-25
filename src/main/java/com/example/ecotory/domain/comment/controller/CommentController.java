package com.example.ecotory.domain.comment.controller;

import com.example.ecotory.domain.comment.dto.request.AddCommentRequest;
import com.example.ecotory.domain.comment.dto.response.comment.*;
import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.service.CommentService;
import com.example.ecotory.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AddCommentResponse> addComment(@RequestAttribute Member member,
                                                         @RequestBody AddCommentRequest request) {

        Comment comment = commentService.addComment(member, request);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(AddCommentResponse.fromEntity(comment, true));
    }


    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable String commentId,
                                                               @RequestAttribute Member member,
                                                               @RequestBody String content) {

        Comment comment = commentService.updateComment(commentId, member, content);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(UpdateCommentResponse.fromEntity(comment, true));
    }

@Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
@DeleteMapping({"/{commentId}"})
public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable String commentId,
                                                           @RequestAttribute Member member) {

    Comment comment = commentService.deleteComment(commentId, member);

    return ResponseEntity.status(HttpStatus.OK) //200
            .body(DeleteCommentResponse.fromEntity(comment, true));
}


    @Operation(summary = "댓글 목록 조회", description = "특정 글의 댓글 목록을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<GetCommentsByPostResponse> getComments(@PathVariable String postId) {

        List<Comment> commentList = commentService.getComments(postId);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetCommentsByPostResponse.fromEntity(commentList, true));
    }

    @Operation(summary = "사용자 댓글 목록 조회", description = "특정 사용자가 작성한 댓글 목록을 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<GetCommentsByMemberResponse> getCommentsByMember(@RequestAttribute Member member) {

        List<Comment> commentListByMember = commentService.getMyComments(member);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetCommentsByMemberResponse.fromEntity(commentListByMember, true));
    }

}