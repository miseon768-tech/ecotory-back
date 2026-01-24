package com.example.ecotory.domain.post.repository;

import com.example.ecotory.domain.post.entity.Post;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    // ID로 글 조회
    Optional<Post> findById(String postId);

    // 특정 멤버의 모든 글 조회
    List<Post> findByMember(Member member);

    // 제목/내용 검색
    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}
