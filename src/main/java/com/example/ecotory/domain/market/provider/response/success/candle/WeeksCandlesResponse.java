package com.example.ecotory.domain.market.provider.response.success.candle;

import com.example.ecotory.domain.market.provider.response.success.candle.data.CandleData;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class WeeksCandlesResponse { // 주 캔들 조회

    CandleData candleData;

    private String firstDayOfPeriod;      // 해당 주의 첫 날 (yyyy-MM-dd)
}
