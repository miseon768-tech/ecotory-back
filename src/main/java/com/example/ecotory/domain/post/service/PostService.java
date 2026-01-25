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
    private final PostAttachmentRepository postAttachmentRepository;

    // 글 작성
    public Post createPost(AddPostRequest request) {

        Post post = request.toEntity();

        return postRepository.save(post);
    }

    // 글 수정
    public Post updatePost(String postId, UpdatePostRequest updatePostRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());


        return postRepository.save(post);
    }

    // 글 삭제
    public Post deletePost(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        postRepository.delete(post);

        return post;
    }

    // 글 전체 목록 조회
    public List<Post> getAllPosts() {

        List<Post> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            throw new NoSuchElementException("커뮤니티 글 없음");
        }

        return postList;
    }

    // 단일 글 조회
    public Post getPost(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        return post;
    }

    // 내가 쓴 글 조회
    public List<Post> getMyPostByMember(Member member) {

        List<Post> mypostList = postRepository.findByMember(member);

        if (mypostList.isEmpty()) {
            throw new NoSuchElementException("내가 쓴 글 없음");
        }

        return mypostList;
    }

    // 키워드로 제목/내용 글 검색
    public List<Post> searchPosts(String keyword) {

        List<Post> postList = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        if (postList.isEmpty()) {
            throw new NoSuchElementException("검색된 글 없음");
        }

        return postList;
    }

    // 파일 올리기
    public List<PostAttachment> uploadFiles(String postId, List<MultipartFile> files) {


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

            String savedFileName = UUID.randomUUID().toString()
                    .replace("-", "") + "_" + file.getOriginalFilename();

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

        return attachments;
    }

    // 조회수 증가
    public Post increaseViewCount(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        post.setViewCount(post.getViewCount() + 1);


        return postRepository.save(post);
    }

    // 글 상단 고정 -- 관리자 기능 : 추후에 관리자 권한 체크 로직 추가 필요
    public Post pinPost(String postId, boolean pinned) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        /*  if (!post.getMember().getId().equals(member)) {
            throw new RuntimeException("글 고정 권한이 없습니다."); //403
        }*/

        if (pinned && !post.isPinned()) {
            long pinnedCount = getAllPosts().stream()
                    .filter(Post::isPinned)
                    .count();

            if (pinnedCount >= 3) {
                throw new IllegalStateException("상단 고정은 최대 3개까지만 가능합니다.");
            }
        }

        post.setPinned(pinned);


        return postRepository.save(post);
    }

}
