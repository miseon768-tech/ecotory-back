package com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableAmountResponse {
    private double availableAmount;
    private boolean success;
}
