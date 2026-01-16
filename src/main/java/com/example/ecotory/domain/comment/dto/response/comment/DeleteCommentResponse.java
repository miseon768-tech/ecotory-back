package com.example.ecotory.domain.comment.dto.response.comment;

import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteCommentResponse {
    private krwAsset KRWAsset;
    private boolean success;
}
