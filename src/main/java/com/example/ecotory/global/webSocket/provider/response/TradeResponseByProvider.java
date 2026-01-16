package com.example.ecotory.global.webSocket.provider.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeResponseByProvider {
    private String type;            // trade
    private String market;          // 페어 코드 (code)
    private double tradePrice;      // 체결 가격
    private double tradeVolume;     // 체결량
    private String askBid;          // 매수/매도 구분
    private double prevClosingPrice;// 전일 종가
    private String change;          // 전일 종가 대비 상승/보합/하락
    private double changePrice;     // 전일 대비 가격 변동 절대값
    private String tradeDate;       // 체결 일자
    private String tradeTime;       // 체결 시각
    private long tradeTimestamp;    // 체결 타임스탬프(ms)
    private long timestamp;         // 수신 타임스탬프(ms)
    private long sequentialId;      // 체결 번호(Unique)
    private double bestAskPrice;    // 최우선 매도 호가
    private double bestAskSize;     // 최우선 매도 잔량
    private double bestBidPrice;    // 최우선 매수 호가
    private double bestBidSize;     // 최우선 매수 잔량
    private String streamType;      // SNAPSHOT / REALTIME
}