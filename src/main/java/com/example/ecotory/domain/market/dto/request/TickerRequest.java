package com.example.ecotory.domain.market.dto.request;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TickerRequest {
    private String market;
    private BigDecimal trade_price;
    private BigDecimal high_price;
    private BigDecimal low_price;
    private BigDecimal opening_price;

}