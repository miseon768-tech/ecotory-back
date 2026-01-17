package com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EvalAmountTimelineResponse {
    private KrwAsset KRWAsset;
    private boolean success;
}
