package com.example.ecotory.domain.coinAsset.dto.response.CoinAsset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalCoinBuyAmountResponse {
    private long totalBuyAmount;
    private boolean success;
}
