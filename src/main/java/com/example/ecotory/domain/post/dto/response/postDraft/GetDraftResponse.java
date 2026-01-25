package com.example.ecotory.domain.post.dto.response.postDraft;

import com.example.ecotory.domain.post.entity.PostDraft;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetDraftResponse {

    private List<PostDraft> draftList;

    public static GetDraftResponse fromEntity(List<PostDraft> response) {
        return GetDraftResponse.builder()
                .draftList(response)
                .build();
    }
}