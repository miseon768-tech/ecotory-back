package com.example.ecotory.domain.comment.service;

import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.entity.CommentLike;
import com.example.ecotory.domain.comment.repository.CommentLikeRepository;
import com.example.ecotory.domain.comment.repository.CommentRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    // 댓글 좋아요
    public CommentLike createCommentLike(String commentId, Member member) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글 없음"));

        Optional<CommentLike> commentLike
                = commentLikeRepository.findByCommentIdAndMemberId(comment.getId(), member.getId());

        if (commentLike.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."); // 400
        }


        CommentLike newLike = CommentLike.builder()
                .commentId(comment.getId())
                .memberId(member.getId())
                .build();

        CommentLike savedLike = commentLikeRepository.save(newLike);


        return savedLike;
    }

    // 댓글 좋아요 취소
    public CommentLike deleteCommentLike(String commentId, Member member) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.")); // 404

        CommentLike existingLike = commentLikeRepository.findByCommentIdAndMemberId(comment.getId(), member.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다.")); // 400

        // 삭제 전 existingLike를 보관
        commentLikeRepository.delete(existingLike);

        return existingLike;
    }

}