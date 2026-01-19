package com.example.ecotory.domain.market.provider.response.success.orderbook;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class OrderbookUnit {
    private Double ask_price;           // 매도 호가
    private Double bid_price;           // 매수 호가
    private Double ask_size;            // 매도 잔량
    private Double bid_size;            // 매수 잔량
    private Double level;               // 해당 호가가 적용된 가격 단위
}

