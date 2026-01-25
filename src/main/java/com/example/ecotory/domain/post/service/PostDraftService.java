package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.post.dto.request.SaveDraftRequest;
import com.example.ecotory.domain.post.entity.PostDraft;
import com.example.ecotory.domain.post.repository.PostDraftRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostDraftService {

    private final PostDraftRepository postDraftRepository;

    // 임시글 저장
    public PostDraft saveDraft(SaveDraftRequest request) {

        PostDraft postDraft = request.toEntity();

        return postDraftRepository.save(postDraft);
    }

    // 작성자 기준 임시글 불러오기
    public List<PostDraft> getDraft(Member member) {
       

        List<PostDraft> draftList = postDraftRepository.findByMemberOrderByUpdatedAtDesc(member);

        return draftList;
    }
}