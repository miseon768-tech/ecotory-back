package com.example.ecotory.global.webSocket.provider.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class OrderbookResponseByProvider {

    private String type;
    private String code; // 마켓 코드
    private double totalAskSize;
    private double totalBidSize;
    private List<OrderbookUnit> orderbookUnits;
    private long timestamp;
    private int level;
    private String streamType;

    @Getter
    @Builder
    public static class OrderbookUnit {
        private double askPrice;
        private double bidPrice;
        private double askSize;
        private double bidSize;
    }
}