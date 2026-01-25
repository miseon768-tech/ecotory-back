package com.example.ecotory.domain.comment.service;

import com.example.ecotory.domain.comment.dto.request.AddCommentRequest;
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
    public Comment addComment(Member member, AddCommentRequest request) {

        postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));

        Comment addComment = request.toEntity();

        return commentRepository.save(addComment);

    }

    // 댓글 수정
    public Comment updateComment(String commentId, Member member, String content) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글 없음"));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        comment.setContent(content);

        return commentRepository.save(comment);

    }

    // 댓글 삭제
    public Comment deleteComment(String commentId, Member member) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글 없음"));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

        return comment;
    }

    // 댓글 목록 조회(특정 글의 댓글들)
    public List<Comment> getComments(String postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));


        return commentRepository.findByPost(post);
    }

    // 댓글 목록 조회(사용자의 댓글들)
    public List<Comment> getMyComments(Member member) {

        return commentRepository.findByMember(member);
    }

}