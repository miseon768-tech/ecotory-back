package com.example.ecotory.global.webSocket.provider.mapper;

import com.example.ecotory.global.webSocket.provider.response.CandleResponseByProvider;
import com.fasterxml.jackson.databind.JsonNode;

public class CandleMapper {

    public static CandleResponseByProvider map(JsonNode json) {
        return CandleResponseByProvider.builder()
                .type(json.get("type").asText())
                .market(json.get("code").asText())
                .candleDateTimeUtc(json.get("candle_date_time_utc").asText())
                .candleDateTimeKst(json.get("candle_date_time_kst").asText())
                .openingPrice(json.get("opening_price").asDouble())
                .highPrice(json.get("high_price").asDouble())
                .lowPrice(json.get("low_price").asDouble())
                .tradePrice(json.get("trade_price").asDouble())
                .candleAccTradeVolume(json.get("candle_acc_trade_volume").asDouble())
                .candleAccTradePrice(json.get("candle_acc_trade_price").asDouble())
                .timestamp(json.get("timestamp").asLong())
                .streamType(json.get("stream_type").asText())
                .build();
    }
}
