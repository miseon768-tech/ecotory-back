package com.example.ecotory.domain.market.provider.response.success.candle;

import com.example.ecotory.domain.market.provider.response.success.candle.data.CandleData;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class MinutesCandlesResponse { // 분 캔들 조회

    CandleData candleData;

    private Integer unit;                       // 캔들 집계 시간 단위(분), 예: 1, 3, 5, 10, 15, 30, 60, 240
}