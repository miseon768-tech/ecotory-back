package com.example.ecotory.domain.market.provider.response.success.tradingPairs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEvent {
    private Boolean warning; // 유의 종목 여부
    private Caution caution; // 주의 종목 정보
}

