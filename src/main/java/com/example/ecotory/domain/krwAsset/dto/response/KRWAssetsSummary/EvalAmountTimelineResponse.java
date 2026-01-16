package com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary;

import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EvalAmountTimelineResponse {
    private krwAsset KRWAsset;
    private boolean success;
}
