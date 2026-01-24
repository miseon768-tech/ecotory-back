package com.example.ecotory.domain.comment.service;

import com.example.ecotory.domain.comment.dto.response.comment.*;
import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.repository.CommentRepository;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    public Comment addComment(Member member, String content, String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);

        return commentRepository.save(comment);

    }

    // 댓글 수정
    public Comment updateComment(String commentId, Member member, String content) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글 없음"));

        if (!comment.getMember().getId().equals(member)) {
            throw new IllegalStateException("댓글 수정 권한 없음"); // 403
        }


        return commentRepository.save(comment);

    }

    // 댓글 삭제
    public DeleteCommentResponse deleteComment(String commentId, Member member) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글 없음"));

        if (!comment.getMember().getId().equals(member)) {
            throw new IllegalStateException("댓글 삭제 권한 없음"); // 403
        }

        commentRepository.delete(comment);

        return DeleteCommentResponse.builder()
                .success(true)
                .build();
    }

    // 댓글 목록 조회(특정 글의 댓글들)
    public GetCommentsByPostResponse getComments(Member member, String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));


        List<Comment> commentList = commentRepository.findByPost(post);


        return GetCommentsByPostResponse.builder()
                .commentList(commentList)
                .success(true)
                .build();
    }

    // 댓글 목록 조회(사용자의 댓글들)
    public GetCommentsByMemberResponse getMyComments(Member member) {

        List<Comment> commentListByMember = commentRepository.findByMember(member);

        return GetCommentsByMemberResponse.builder()
                .commentListByMember(commentListByMember)
                .success(true)
                .build();
    }

}