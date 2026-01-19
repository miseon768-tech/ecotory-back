package com.example.ecotory.domain.market.dto.request;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

// 프론트에서 뭘 받을지 정해야함
public class CandleRequest {
    private String market;
    private BigDecimal trade_price;
    private BigDecimal high_price;
    private BigDecimal low_price;
    private BigDecimal opening_price;

}