package com.example.ecotory.global.webSocket.provider.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TickerResponseByProvider {

    private String type;
    private String code; // 마켓 코드
    private double openingPrice;
    private double highPrice;
    private double lowPrice;
    private double tradePrice;
    private double prevClosingPrice;
    private double accTradePrice;
    private double accTradePrice24h;
    private double accTradeVolume;
    private double accTradeVolume24h;
    private String change;
    private double changePrice;
    private double signedChangePrice;
    private double changeRate;
    private double signedChangeRate;
    private String askBid;
    private double tradeVolume;
    private double accAskVolume;
    private double accBidVolume;
    private double highest52WeekPrice;
    private String highest52WeekDate;
    private double lowest52WeekPrice;
    private String lowest52WeekDate;
    private String marketState;
    private boolean isTradingSuspended;
    private String delistingDate;
    private String marketWarning;
    private long timestamp;
    private String streamType;
}