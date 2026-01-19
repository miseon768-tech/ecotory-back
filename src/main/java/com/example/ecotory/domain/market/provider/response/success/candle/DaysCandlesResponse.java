
package com.example.ecotory.domain.market.provider.response.success.candle;

import com.example.ecotory.domain.market.provider.response.success.candle.data.CandleData;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DaysCandlesResponse { // 일 캔들 조회

    CandleData candleData;

    private Double prevClosingPrice;       // 전일 종가
    private Double changePrice;             // 전일 종가 대비 가격 변화
    private Double changeRate;              // 전일 종가 대비 가격 변화율
    private Double convertedTradePrice;    // 선택적 필드, 환산 종가 (요청 시 존재, 없을 수도 있음)
}