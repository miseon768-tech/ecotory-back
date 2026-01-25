package com.example.ecotory.domain.post.controller;

import com.example.ecotory.domain.post.dto.request.AddPostRequest;
import com.example.ecotory.domain.post.dto.request.UpdatePostRequest;
import com.example.ecotory.domain.post.dto.response.post.*;
import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.post.entity.PostAttachment;
import com.example.ecotory.domain.post.service.PostService;
import com.example.ecotory.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post", description = "커뮤니티 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;


    @Operation(summary = "글 작성", description = "커뮤니티에 새 글을 작성합니다.")
    @PostMapping
    public ResponseEntity<AddPostResponse> createPost(@RequestBody AddPostRequest request)  {

        Post post = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(AddPostResponse.fromEntity(post));
    }


    @Operation(summary = "글 수정", description = "커뮤니티에 작성된 글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable String postId,
                                                         @RequestBody UpdatePostRequest updatePostRequest)  {

        Post post = postService.updatePost(postId, updatePostRequest);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(UpdatePostResponse.fromEntity(post));
    }

    @Operation(summary = "글 삭제", description = "커뮤니티에 작성된 글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse> deletePost(@PathVariable String postId)  {

        Post post = postService.deletePost(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(DeletePostResponse.fromEntity(post));
    }


    @Operation(summary = "글 전체 목록 조회", description = "커뮤니티에 작성된 모든 글을 조회합니다.")
    @GetMapping
    public ResponseEntity<GetAllPostResponse> getPosts() {

        List<Post> response = postService.getAllPosts();

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetAllPostResponse.fromEntity(response));
    }

    @Operation(summary = "단일 글 조회", description = "커뮤니티에 작성된 특정 글을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable String postId) {

        Post response = postService.getPost(postId);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetPostResponse.fromEntity(response));
    }

    @Operation(summary = "내가 쓴 글 조회", description = "특정 사용자가 작성한 글 목록을 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<GetMyPostResponse> getMyPostByMember(@RequestAttribute Member member)  {

        List<Post> response = postService.getMyPostByMember(member);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetMyPostResponse.fromEntity(response));
    }

    @Operation(summary = "키워드로 글 검색", description = "제목 또는 내용에 특정 키워드가 포함된 글을 조회합니다.")
    @GetMapping("/keyword")
    public ResponseEntity<GetPostByKeywordResponse> searchPosts(@RequestParam String keyword) {

        List<Post> response = postService.searchPosts(keyword);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(GetPostByKeywordResponse.fromEntity(response));
    }

    @Operation(summary = "파일 업로드", description = "게시글에 파일/이미지를 업로드합니다.")
    @PostMapping("/{postId}/attachments")
    public ResponseEntity<UploadFilesResponse> uploadPostFiles(@PathVariable String postId,
                                                               @RequestParam("files") List<MultipartFile> files)  {

        List<PostAttachment> response = postService.uploadFiles(postId, files);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(UploadFilesResponse.fromEntity(response));
    }

    @Operation(summary = "글 조회수 증가", description = "특정 글의 조회수를 증가시킵니다.")
    @PostMapping("/{postId}/view")
    public ResponseEntity<IncreaseViewCountResponse> increaseViewCount(@PathVariable String postId)  {

        Post response = postService.increaseViewCount(postId);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(IncreaseViewCountResponse.fromEntity(response));
    }

    @Operation(summary = "글 상단 고정", description = "특정 글을 상단에 고정/해제합니다.")
    @PostMapping("/{postId}/pin")
    public ResponseEntity<PinPostResponse> pinPost(@PathVariable String postId,
                                                   @RequestParam boolean pinned)  {

        Post response = postService.pinPost(postId, pinned);

        return ResponseEntity
                .status(HttpStatus.OK) //200
                .body(PinPostResponse.fromEntity(response));
    }

}
