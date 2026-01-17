package com.example.ecotory.global.webSocket.provider.mapper;

import com.example.ecotory.global.webSocket.provider.response.TradeResponseByProvider;
import com.fasterxml.jackson.databind.JsonNode;

public class TradeMapper {

    public static TradeResponseByProvider map(JsonNode json) {
        return TradeResponseByProvider.builder()
                .type(json.get("type").asText())
                .market(json.get("code").asText())
                .tradePrice(json.get("trade_price").asDouble())
                .tradeVolume(json.get("trade_volume").asDouble())
                .askBid(json.get("ask_bid").asText())
                .prevClosingPrice(json.get("prev_closing_price").asDouble())
                .change(json.get("change").asText())
                .changePrice(json.get("change_price").asDouble())
                .tradeDate(json.get("trade_date").asText())
                .tradeTime(json.get("trade_time").asText())
                .tradeTimestamp(json.get("trade_timestamp").asLong())
                .timestamp(json.get("timestamp").asLong())
                .sequentialId(json.get("sequential_id").asLong())
                .bestAskPrice(json.get("best_ask_price").asDouble())
                .bestAskSize(json.get("best_ask_size").asDouble())
                .bestBidPrice(json.get("best_bid_price").asDouble())
                .bestBidSize(json.get("best_bid_size").asDouble())
                .streamType(json.get("stream_type").asText())
                .build();
    }
}
