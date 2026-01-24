package com.example.ecotory.domain.post.repository;

import com.example.ecotory.domain.post.entity.PostDraft;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDraftRepository extends JpaRepository<PostDraft, String> {
    List<PostDraft> findByMemberOrderByUpdatedAtDesc(Member member);

}
