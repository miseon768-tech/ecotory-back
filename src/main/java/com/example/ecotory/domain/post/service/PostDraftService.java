package com.example.ecotory.domain.post.service;

import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.post.dto.response.postDraft.GetDraftResponse;
import com.example.ecotory.domain.post.dto.response.postDraft.SaveDraftResponse;
import com.example.ecotory.domain.post.entity.PostDraft;
import com.example.ecotory.domain.post.repository.PostDraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class PostDraftService {

    private final PostDraftRepository postDraftRepository;
    private final MemberRepository memberRepository;

    // 임시글 저장
    public SaveDraftResponse saveDraft(String subject, String title, String content) {

       

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
    public GetDraftResponse getDraft(String subject) {
       

        List<PostDraft> draftList = postDraftRepository
                .findByMemberOrderByUpdatedAtDesc(subject);

        return GetDraftResponse.builder()
                .draftList(draftList)
                .build();
    }
}