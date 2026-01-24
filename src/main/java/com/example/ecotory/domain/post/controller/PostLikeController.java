package com.example.ecotory.domain.post.controller;

import com.example.ecotory.domain.post.dto.response.postLike.GetMyLikedPostsResponse;
import com.example.ecotory.domain.post.dto.response.postLike.GetPostLikeCountResponse;
import com.example.ecotory.domain.post.dto.response.postLike.LikePostResponse;
import com.example.ecotory.domain.post.dto.response.postLike.UnLikePostResponse;
import com.example.ecotory.domain.post.service.PostLikeService;
import com.example.ecotory.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "PostLike", description = "글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/like")
public class PostLikeController {


    private final PostLikeService postLikeService;

    @Operation(summary = "글 좋아요", description = "특정 글에 좋아요를 누릅니다.")
    @PostMapping("/{postId}")
    public ResponseEntity<LikePostResponse> likePost(@PathVariable String postId,
                                                     @RequestAttribute Member member) {

        LikePostResponse response = postLikeService.likePost(postId, member);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "글 좋아요 취소", description = "특정 글에 대한 좋아요를 취소합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<UnLikePostResponse> unlikePost(@PathVariable String postId,
                                                         @RequestAttribute Member member) {

        UnLikePostResponse response = postLikeService.unlikePost(postId, member);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "내가 좋아요한 글 조회", description = "내가 좋아요한 글 목록을 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<GetMyLikedPostsResponse> getMyLikedPosts(@RequestAttribute Member member) {

        GetMyLikedPostsResponse response = postLikeService.getMyLikedPosts(member);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "글 좋아요 수 조회", description = "특정 글의 좋아요 수를 조회합니다.")
    @GetMapping("/count/{postId}")
    public ResponseEntity<GetPostLikeCountResponse> getPostLikeCount(@PathVariable String postId) {

        GetPostLikeCountResponse response = postLikeService.getPostLikeCount(postId);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }
}
