package com.example.ecotory.domain.tradingPair.dto.response;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
TradingPairResponse { // 페어 목록(전체 목록) 조회

    private List<TradingPair> tradingPairs;
    private boolean success;
}