package com.example.ecotory.domain.market.provider.response.success.candle.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CandleData {
    private String market;                 // 조회 대상 거래쌍 코드, 예: KRW-BTC
    private String candleDateTimeUtc;   // 캔들 구간 시작 시각 (UTC 기준), 형식: yyyy-MM-dd'T'HH:mm:ss
    private String candleDateTimeKst;   // 캔들 구간 시작 시각 (KST 기준), 형식: yyyy-MM-dd'T'HH:mm:ss
    private Double openingPrice;          // 시가, 해당 캔들의 첫 거래 가격
    private Double highPrice;             // 고가, 해당 캔들의 최고 거래 가격
    private Double lowPrice;              // 저가, 해당 캔들의 최저 거래 가격
    private Double tradePrice;            // 종가, 해당 캔들의 마지막 거래 가격
    private Long timestamp;                // 해당 캔들의 마지막 틱이 저장된 시각 타임스탬프(ms)
    private Double candleAccTradePrice; // 캔들 동안의 누적 거래 금액
    private Double candleAccTradeVolume;// 캔들 동안 누적 거래된 디지털 자산 수량
}
