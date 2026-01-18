package com.example.ecotory.domain.assetPriceStream.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinEvalAmountResponse {

    private boolean success;
    private double evalAmount;
}
