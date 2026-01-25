package com.example.ecotory.domain.post.dto.response.postDraft;

import com.example.ecotory.domain.post.entity.PostDraft;
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


    public static SaveDraftResponse fromEntity(PostDraft response) {
        return SaveDraftResponse.builder()
                .id(response.getId())
                .title(response.getTitle())
                .savedAt(response.getUpdatedAt())
                .status("DRAFT")
                .build();
    }
}