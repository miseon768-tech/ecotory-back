package com.example.ecotory.domain.market.provider.response.success.candle;

import com.example.ecotory.domain.market.provider.response.success.candle.data.CandleData;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class YearsCandlesResponse { // 연 캔들 조회

    CandleData candleData;

    private String firstDayOfPeriod;      // 해당 연도의 첫 날 (yyyy-MM-dd)
}