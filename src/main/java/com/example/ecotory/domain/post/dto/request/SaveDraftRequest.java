package com.example.ecotory.domain.post.dto.request;

import com.example.ecotory.domain.post.entity.PostDraft;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveDraftRequest {
    private String title;
    private String content;

    public PostDraft toEntity() {
        return PostDraft.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
