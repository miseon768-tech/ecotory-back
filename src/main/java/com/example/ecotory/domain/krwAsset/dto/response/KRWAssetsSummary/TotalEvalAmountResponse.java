package com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalEvalAmountResponse {
    private double totalEvalAmount;
    private boolean success;
}
