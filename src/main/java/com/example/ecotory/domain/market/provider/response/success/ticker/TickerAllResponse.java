package com.example.ecotory.domain.market.provider.response.success.ticker;

import com.example.ecotory.domain.market.provider.response.success.ticker.data.TickerData;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TickerAllResponse { // 마켓 단위 현재가 조회

   TickerData tickerData;
}

