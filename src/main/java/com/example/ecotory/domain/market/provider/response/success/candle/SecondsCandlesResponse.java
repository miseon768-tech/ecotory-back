package com.example.ecotory.domain.market.provider.response.success.candle;

import com.example.ecotory.domain.market.provider.response.success.candle.data.CandleData;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class SecondsCandlesResponse { // 초 캔들 조회

    CandleData candleData;
    private Integer unit;

}