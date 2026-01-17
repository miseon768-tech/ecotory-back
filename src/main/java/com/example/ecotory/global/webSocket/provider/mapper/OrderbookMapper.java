package com.example.ecotory.global.webSocket.provider.mapper;

import com.example.ecotory.global.webSocket.provider.response.OrderbookResponseByProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class OrderbookMapper {

    public static OrderbookResponseByProvider map(JsonNode json, ObjectMapper objectMapper) throws Exception {

        List<OrderbookResponseByProvider.OrderbookUnit> units = objectMapper
                .readerForListOf(OrderbookResponseByProvider.OrderbookUnit.class)
                .readValue(json.get("orderbook_units"));

        return OrderbookResponseByProvider.builder()
                .type(json.get("type").asText())
                .code(json.get("code").asText())
                .totalAskSize(json.get("total_ask_size").asDouble())
                .totalBidSize(json.get("total_bid_size").asDouble())
                .orderbookUnits(units)
                .timestamp(json.get("timestamp").asLong())
                .level(json.get("level").asInt())
                .streamType(json.get("stream_type").asText())
                .build();
    }
}
