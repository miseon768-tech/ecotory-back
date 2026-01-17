package com.example.ecotory.domain.assetPriceStream.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinProfitResponse {

    private boolean success;

    private double coinBalance;
    private double currentPrice;
    private double avgPrice;
    private double profit;
}
