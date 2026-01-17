package com.example.ecotory.global.webSocket.provider.mapper;

import com.example.ecotory.global.webSocket.provider.response.TickerResponseByProvider;
import com.fasterxml.jackson.databind.JsonNode;

public class TickerMapper {

    public static TickerResponseByProvider map(JsonNode json) {
        return TickerResponseByProvider.builder()
                .type(json.get("type").asText())
                .code(json.get("code").asText())
                .openingPrice(json.get("opening_price").asDouble())
                .highPrice(json.get("high_price").asDouble())
                .lowPrice(json.get("low_price").asDouble())
                .tradePrice(json.get("trade_price").asDouble())
                .prevClosingPrice(json.get("prev_closing_price").asDouble())
                .accTradePrice(json.get("acc_trade_price").asDouble())
                .accTradePrice24h(json.get("acc_trade_price_24h").asDouble())
                .accTradeVolume(json.get("acc_trade_volume").asDouble())
                .accTradeVolume24h(json.get("acc_trade_volume_24h").asDouble())
                .change(json.get("change").asText())
                .changePrice(json.get("change_price").asDouble())
                .signedChangePrice(json.get("signed_change_price").asDouble())
                .changeRate(json.get("change_rate").asDouble())
                .signedChangeRate(json.get("signed_change_rate").asDouble())
                .askBid(json.get("ask_bid").asText())
                .tradeVolume(json.get("trade_volume").asDouble())
                .accAskVolume(json.get("acc_ask_volume").asDouble())
                .accBidVolume(json.get("acc_bid_volume").asDouble())
                .highest52WeekPrice(json.get("highest_52_week_price").asDouble())
                .highest52WeekDate(json.get("highest_52_week_date").asText())
                .lowest52WeekPrice(json.get("lowest_52_week_price").asDouble())
                .lowest52WeekDate(json.get("lowest_52_week_date").asText())
                .marketState(json.get("market_state").asText())
                .isTradingSuspended(json.get("is_trading_suspended").asBoolean())
                .delistingDate(json.get("delisting_date").isNull() ? null : json.get("delisting_date").asText())
                .marketWarning(json.get("market_warning").asText())
                .timestamp(json.get("timestamp").asLong())
                .streamType(json.get("stream_type").asText())
                .build();
    }
}