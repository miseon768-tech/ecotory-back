package com.example.ecotory.domain.post.dto.response.postDraft;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SaveDraftResponse {

    private String id;             // 임시글 ID
    private String title;          // 임시글 제목
    private LocalDateTime savedAt; // 임시 저장 시간
    private String status;         // 임시 상태, 항상 "DRAFT"로 전달


}