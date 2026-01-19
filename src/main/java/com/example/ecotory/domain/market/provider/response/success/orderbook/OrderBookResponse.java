package com.example.ecotory.domain.market.provider.response.success.orderbook;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderBookResponse { // 호가 조회

    private String market;                 // 거래쌍 코드, 예: KRW-BTC
    private Long timestamp;                // 조회 요청 시각 타임스탬프(ms)
    private Double total_ask_size;         // 전체 매도 잔량 합계
    private Double total_bid_size;         // 전체 매수 잔량 합계
    private List<OrderbookUnit> orderbook_units; // 호가 단위별 정보 리스트
    private Double level;                  // 호가 모아보기 단위 (0: 단위 없음, 1 이상: 지정된 가격 단위)
}