package com.example.ecotory.domain.comment.service;

import com.example.ecotory.domain.comment.dto.response.commentLike.CommentLikeCancelResponse;
import com.example.ecotory.domain.comment.dto.response.commentLike.CommentLikeResponse;
import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.entity.CommentLike;
import com.example.ecotory.domain.comment.repository.CommentLikeRepository;
import com.example.ecotory.domain.comment.repository.CommentRepository;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    // 댓글 좋아요
    public CommentLikeResponse createCommentLike(String commentId, String subject) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.")); //404

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.")); // 404


        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndMember(comment, member);

        if (commentLike.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."); // 400
        }


        CommentLike newLike = CommentLike.builder()
                .comment(comment)
                .member(member)
                .build();

        CommentLike savedLike = commentLikeRepository.save(newLike);


        return CommentLikeResponse.builder()
                .commentLike(savedLike)
                .success(true)
                .build();
    }

    // 댓글 좋아요 취소
    public CommentLikeCancelResponse deleteCommentLike(String commentId, String subject) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.")); //404

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.")); // 404

        CommentLike existingLike = commentLikeRepository.findByCommentAndMember(comment, member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다.")); // 400

        // 삭제 전 existingLike를 보관
        commentLikeRepository.delete(existingLike);

        return CommentLikeCancelResponse.builder()
                .commentLike(existingLike)
                .success(true)
                .build();
    }

}