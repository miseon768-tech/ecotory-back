package com.example.ecotory.global.webSocket.provider.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CandleResponseByProvider {
    private String type;                  // candle.1s / candle.1m / candle.3m ...
    private String market;                // 마켓 코드
    private String candleDateTimeUtc;     // UTC 기준
    private String candleDateTimeKst;     // KST 기준
    private double openingPrice;          // 시가
    private double highPrice;             // 고가
    private double lowPrice;              // 저가
    private double tradePrice;            // 종가
    private double candleAccTradeVolume;  // 누적 거래량
    private double candleAccTradePrice;   // 누적 거래 금액
    private long timestamp;               // 수신 타임스탬프
    private String streamType;            // SNAPSHOT / REALTIME
}