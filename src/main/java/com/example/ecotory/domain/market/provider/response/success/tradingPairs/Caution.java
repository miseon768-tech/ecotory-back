package com.example.ecotory.domain.market.provider.response.success.tradingPairs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caution {
    private Boolean priceFluctuations;           // 가격 급등락 경보
    private Boolean tradingVolumeSoaring;        // 거래량 급증 경보
    private Boolean depositAmountSoaring;        // 입금량 급증 경보
    private Boolean globalPriceDifferences;      // 국내외 가격 차이 경보
    private Boolean concentrationOfSmallAccounts; // 소수 계정 집중 거래 경보
}
