package com.example.ecotory.domain.post.repository;

import com.example.ecotory.domain.post.entity.PostLike;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, String> {

    // 특정 글에 대해 특정 멤버가 좋아요를 눌렀는지 확인
    Optional<PostLike> findByPostAndMember(String postId, Member member);

    // 특정 멤버가 좋아요한 글 목록 조회
    List<PostLike> findByMember(Member member);

    // 특정 글의 좋아요 수 카운트
    Long countByPost(String postId);

}
