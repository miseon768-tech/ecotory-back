package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteCommentResponse {
    private KRWAsset KRWAsset;
    private boolean success;
}
