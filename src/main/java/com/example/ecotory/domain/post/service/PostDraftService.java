package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.post.dto.response.postDraft.GetDraftResponse;
import com.example.ecotory.domain.post.dto.response.postDraft.SaveDraftResponse;
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
    private final MemberRepository memberRepository;

    // 임시글 저장
    public SaveDraftResponse saveDraft(Member member, String title, String content) {

       

        PostDraft postDraft = PostDraft.builder()
                .title(title)
                .content(content)
                .build();

        postDraftRepository.save(postDraft);

        return SaveDraftResponse.builder()
                .id(postDraft.getId())
                .title(postDraft.getTitle())
                .savedAt(postDraft.getUpdatedAt())
                .status("DRAFT")
                .build();
    }

    // 작성자 기준 임시글 불러오기
    public GetDraftResponse getDraft(Member member) {
       

        List<PostDraft> draftList = postDraftRepository
                .findByMemberOrderByUpdatedAtDesc(member);

        return GetDraftResponse.builder()
                .draftList(draftList)
                .build();
    }
}