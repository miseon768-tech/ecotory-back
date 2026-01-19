package com.example.ecotory.domain.market.provider.response.success.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradesTicksResponse { // 최근 체결 내역 조회

    private String market;             // 페어(거래쌍) 코드
    private String tradeDateUtc;       // 체결 일자 (UTC 기준)
    private String tradeTimeUtc;       // 체결 시각 (UTC 기준)
    private Long timestamp;            // 체결 시각의 밀리초 단위 타임스탬프
    private Double tradePrice;         // 최근 체결 가격
    private Double tradeVolume;        // 최근 거래 수량
    private Double prevClosingPrice;   // 전일 종가 (UTC 0시 기준)
    private Double changePrice;        // 전일 종가 대비 가격 변화
    private String askBid;             // 매수/매도 주문 구분 (ASK, BID)
    private Long sequentialId;         // 체결의 유일 식별자
}