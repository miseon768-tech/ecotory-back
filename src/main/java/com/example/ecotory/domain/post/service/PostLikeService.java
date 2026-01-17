package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.post.dto.response.postLike.GetMyLikedPostsResponse;
import com.example.ecotory.domain.post.dto.response.postLike.GetPostLikeCountResponse;
import com.example.ecotory.domain.post.dto.response.postLike.LikePostResponse;
import com.example.ecotory.domain.post.dto.response.postLike.UnLikePostResponse;
import com.example.ecotory.domain.post.entity.PostLike;
import com.example.ecotory.domain.post.repository.PostLikeRepository;
import com.example.ecotory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    // 좋아요 누르기
    public LikePostResponse likePost(String postId, String subject) {

       

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        Optional<PostLike> postLike = postLikeRepository.findByPostAndMember(postId, subject);

        if (postLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다."); // 400
        }

        PostLike newLike = PostLike.builder()
                .postId(postId)
                .memberId(subject)
                .build();

        PostLike savedLike = postLikeRepository.save(newLike);

        return LikePostResponse.builder()
                .postLike(savedLike)
                .success(true)
                .build();
    }

    // 좋아요 취소
    public UnLikePostResponse unlikePost(String postId, String subject) {

       

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        PostLike postLike = postLikeRepository.findByPostAndMember(postId, subject)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다.")); // 400

        postLikeRepository.delete(postLike);

        return UnLikePostResponse.builder()
                .postLike(null)
                .success(true)
                .build();
    }

    // 내가 좋아요한 글 조회
    public GetMyLikedPostsResponse getMyLikedPosts(String subject) {

       

        List<PostLike> likedPosts = postLikeRepository.findByMember(subject);

        List<String> postList = likedPosts.stream()
                .map(PostLike::getPostId)
                .collect(Collectors.toList());

        return GetMyLikedPostsResponse.builder()
                .postList(postList)
                .success(true)
                .build();
    }

    // 글 좋아요 수 조회
    public GetPostLikeCountResponse getPostLikeCount(String postId) {

        postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        Long likeCount = postLikeRepository.countByPost(postId);

        return GetPostLikeCountResponse.builder()
                .likeCount(likeCount)
                .success(true)
                .build();
    }
}