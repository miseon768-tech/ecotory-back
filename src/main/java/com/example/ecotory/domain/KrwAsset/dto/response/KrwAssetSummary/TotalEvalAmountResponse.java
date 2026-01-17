package com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary;

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
