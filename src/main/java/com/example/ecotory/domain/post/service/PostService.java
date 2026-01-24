package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.post.dto.request.AddPostRequest;
import com.example.ecotory.domain.post.dto.request.UpdatePostRequest;
import com.example.ecotory.domain.post.dto.response.post.*;
import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.post.entity.PostAttachment;
import com.example.ecotory.domain.post.repository.PostAttachmentRepository;
import com.example.ecotory.domain.post.repository.PostRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostAttachmentRepository postAttachmentRepository;

    // 글 작성
    public AddPostResponse createPost(Member member, AddPostRequest addPostRequest) {

        Post post = new Post();
        post.setTitle(addPostRequest.getTitle());
        post.setContent(addPostRequest.getContent());

        Post saved = postRepository.save(post);

        return AddPostResponse.builder().post(saved).success(true).build();
    }

    // 글 수정
    public UpdatePostResponse updatePost(String postId, Member member, UpdatePostRequest updatePostRequest) {

       

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        if (!post.getMemberId().equals(member)) {
            throw new IllegalStateException("글 수정 권한 없음"); //403
        }


        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());

        Post updated = postRepository.save(post);

        return UpdatePostResponse.builder().post(updated).success(true).build();
    }

    // 글 삭제
    public DeletePostResponse deletePost(String postId, Member member) {

       

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        if (!post.getMemberId().equals(member)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "글 삭제 권한이 없습니다."); //403
        }

        postRepository.delete(post);

        return DeletePostResponse.builder().success(true).build();
    }

    // 글 전체 목록 조회
    public GetAllPostResponse getAllPosts(Member member) {

       

        List<Post> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            throw new NoSuchElementException("커뮤니티 글 없음");
        }

        return GetAllPostResponse.builder().postList(postList).success(true).build();
    }

    // 단일 글 조회
    public GetPostResponse getPost(String postId, Member member) {

       

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        return GetPostResponse.builder()
                .post(post)
                .success(true)
                .build();
    }

    // 내가 쓴 글 조회
    public GetMyPostResponse getMyPostByMember(Member member) {

       

        List<Post> mypostList = postRepository.findByMember(member);

        if (mypostList.isEmpty()) {
            throw new NoSuchElementException("내가 쓴 글 없음");
        }

        return GetMyPostResponse.builder().postList(mypostList).success(true).build();
    }

    // 키워드로 제목/내용 글 검색
    public GetPostByKeywordResponse searchPosts(Member member, String keyword) {

       

        List<Post> postList = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        if (postList.isEmpty()) {
            throw new NoSuchElementException("검색된 글 없음");
        }

        return GetPostByKeywordResponse.builder()
                .postList(postList)
                .success(true)
                .build();
    }

    // 파일 올리기
    public UploadFilesResponse uploadFiles(String postId, Member member, List<MultipartFile> files) {

       

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다."); //400
        }

        // 업로드 디렉토리 생성
        Path uploadPath = Path.of(System.getProperty("user.home"), "ecotory", "post", postId);

        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("업로드 폴더 생성 실패", e); //500
        }

        // 파일 저장 및 DB 기록
        List<PostAttachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String savedFileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();

            try {
                file.transferTo(uploadPath.resolve(savedFileName));
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패", e); //500
            }

            PostAttachment attachment = PostAttachment.builder()
                    .postId(postId)
                    .fileName(file.getOriginalFilename())
                    .fileSize(file.getSize())
                    .fileContentType(file.getContentType())
                    .fileUrl("/files/post/" + postId + "/" + savedFileName)
                    .build();

            attachments.add(attachment);
        }

        postAttachmentRepository.saveAll(attachments);

        return UploadFilesResponse.builder()
                .fileUrls(attachments.stream()
                        .map(PostAttachment::getFileUrl)
                        .toList())
                .success(true)
                .build();
    }

    // 조회수 증가
    public IncreaseViewCountResponse increaseViewCount(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        post.setViewCount(post.getViewCount() + 1);

        postRepository.save(post);

        return IncreaseViewCountResponse.builder()
                .viewCount(post.getViewCount())
                .success(true)
                .build();
    }

    // 글 상단 고정 -- 관리자 기능 : 추후에 관리자 권한 체크 로직 추가 필요
    public PinPostResponse pinPost(String postId, boolean pinned) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

/*        if (!post.getMember().getId().equals(member)) {
            throw new RuntimeException("글 고정 권한이 없습니다."); //403
        }*/

        post.setPinned(pinned);
        postRepository.save(post);

        return PinPostResponse.builder()
                .pinned(post.isPinned())
                .success(true)
                .build();
    }

}
