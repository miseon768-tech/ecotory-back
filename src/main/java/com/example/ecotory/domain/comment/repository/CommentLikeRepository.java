package com.example.ecotory.domain.comment.repository;

import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.comment.entity.CommentLike;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
}
