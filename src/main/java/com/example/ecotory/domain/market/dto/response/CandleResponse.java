package com.example.ecotory.domain.market.dto.response;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

// 프론트에 뭘 줄지 정해야함
public class CandleResponse {
    private String market;
    private BigDecimal trade_price;
    private BigDecimal high_price;
    private BigDecimal low_price;
    private BigDecimal opening_price;

}