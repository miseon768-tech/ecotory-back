package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.post.entity.PostLike;
import com.example.ecotory.domain.post.repository.PostLikeRepository;
import com.example.ecotory.domain.post.repository.PostRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    // 좋아요 누르기
    public PostLike likePost(String postId, Member member) {

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        Optional<PostLike> postLike = postLikeRepository.findByPostAndMember(postId, member);

        if (postLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다."); // 400
        }

        PostLike newLike = PostLike.builder()
                .postId(postId)
                .memberId(member.getId())
                .build();


        return postLikeRepository.save(newLike);
    }

    // 좋아요 취소
    public PostLike unlikePost(String postId, Member member) {

       

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        PostLike postLike = postLikeRepository.findByPostAndMember(postId, member)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다.")); // 400

        postLikeRepository.delete(postLike);

        return postLike;

    }

    // 내가 좋아요한 글 조회
    public List<String> getMyLikedPosts(Member member) {

        List<String> postList = postLikeRepository.findByMember(member)
                .stream()
                .map(PostLike::getPostId)
                .toList();

        return postList;
    }

    // 글 좋아요 수 조회
    public Long getPostLikeCount(String postId) {

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        return postLikeRepository.countByPost(postId);
    }
}