package com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalBuyAmountResponse {
    private double totalBuyAmount;
    private boolean success;
}
