package com.example.ecotory.domain.comment.repository;

import com.example.ecotory.domain.comment.entity.Comment;
import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    // 특정 게시물의 모든 댓글 조회
    List<Comment> findByPost(Post post);

    // 특정 멤버의 모든 댓글 조회
    List<Comment> findByMember(Member member);

    // 특정 게시물의 삭제되지 않은 댓글 조회
    List<Comment> findByPostIdAndDeletedAtIsNull(String postId);
}
