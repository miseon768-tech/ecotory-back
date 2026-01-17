package com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalProfitRateResponse {
    private double totalProfitRate;
    private boolean success;
}
