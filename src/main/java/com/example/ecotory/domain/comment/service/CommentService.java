package com.example.ecotory.domain.comment.service;

import com.example.ecotory.domain.comment.dto.request.AddCommentRequest;
import com.example.ecotory.domain.comment.dto.request.UpdateCommentRequest;
import com.example.ecotory.domain.comment.dto.response.comment.*;
import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.repository.CommentRepository;
import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.post.repository.PostRepository;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    public AddCommentResponse addComment(String subject, AddCommentRequest addCommentRequest) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버 없음")); // 404

        Post post = postRepository.findById(addCommentRequest.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "커뮤니티 없음")); // 404


        Comment comment = addCommentRequest.toEntity(member, post);

        Comment saved = commentRepository.save(comment);

        return AddCommentResponse.builder()
                .comment(saved)
                .success(true)
                .build();
    }

    // 댓글 수정
    public UpdateCommentResponse updateComment(String commentId, String subject, UpdateCommentRequest updateCommentRequest) {


        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버 없음")); // 404


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글 없음")); // 404

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 수정 권한 없음"); // 403
        }


        Comment updated = commentRepository.save(updateCommentRequest.toEntity(member));

        return UpdateCommentResponse.builder()
                .comment(updated)
                .success(true)
                .build();
    }

    // 댓글 삭제
    public DeleteCommentResponse deleteComment(String commentId, String subject) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버 없음")); // 404

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글 없음")); // 404

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 삭제 권한 없음"); // 403
        }

        commentRepository.delete(comment);

        return DeleteCommentResponse.builder()
                .success(true)
                .build();
    }

    // 댓글 목록 조회(특정 글의 댓글들)
    public GetCommentsByPostResponse getComments(String subject, String postId) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버 없음")); // 404

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "커뮤니티 글 없음")); // 404


        List<Comment> commentList = commentRepository.findByPost(post);


        return GetCommentsByPostResponse.builder()
                .commentList(commentList)
                .success(true)
                .build();
    }

    // 댓글 목록 조회(사용자의 댓글들)
    public GetCommentsByMemberResponse getMyComments(String subject) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버 없음")); // 404

        List<Comment> commentListByMember = commentRepository.findByMember(member);

        return GetCommentsByMemberResponse.builder()
                .commentListByMember(commentListByMember)
                .success(true)
                .build();
    }

}