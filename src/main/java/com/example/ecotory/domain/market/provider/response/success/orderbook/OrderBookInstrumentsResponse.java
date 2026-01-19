package com.example.ecotory.domain.market.provider.response.success.orderbook;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderBookInstrumentsResponse { // 호가 정책 조회

    private String market;                 // 거래쌍 코드, 예: KRW-BTC
    private String quote_currency;         // 해당 페어의 마켓 통화 코드, 예: KRW, BTC, USDT
    private String tick_size;              // 해당 페어에 적용되는 호가 단위, 예: "1000"
    private List<String> supported_levels; // 지원되는 호가 모아보기 단위 리스트, 예: ["0", "10000", "100000"]
}