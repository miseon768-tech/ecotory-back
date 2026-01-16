package com.example.ecotory.global.webSocket.provider.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import com.example.ecotory.global.webSocket.provider.response.TickerResponseByProvider;
import com.example.ecotory.global.webSocket.push.TickerPushService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TickerServiceByProvider {

    private final Map<String, TickerResponseByProvider> tickerCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient wsClient;
    private final TickerPushService tickerPushService;

    private List<String> markets;
    private final TradingPairService tradingPairService;


    @PostConstruct
    public void init() {
        try {
            // MarketService에서 KRW 마켓 리스트 가져오기
            this.markets = tradingPairService.getMarkets()
                    .stream()
                    .map(TradingPair::getMarket)
                    .toList();

            connectWebSocket();
        } catch (Exception e) {
            log.error("KRW 마켓 리스트 조회 실패", e);
        }
    }

    private void connectWebSocket() {
        try {
            wsClient = new WebSocketClient(new URI("wss://api.upbit.com/websocket/v1")) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    log.info("Ticker WebSocket 연결 성공!");
                    String ticket = "{\"ticket\":\"upbit-ticker\"}";
                    String subscribe = "{\"type\":\"ticker\",\"codes\":" + markets.toString() + "}";
                    String format = "{\"format\":\"DEFAULT\"}";
                    send("[" + ticket + "," + subscribe + "," + format + "]");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        JsonNode json = objectMapper.readTree(message);

                        TickerResponseByProvider ticker = TickerResponseByProvider.builder()
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

                        tickerCache.put(ticker.getCode(), ticker);
                        tickerPushService.pushTicker(ticker);

                    } catch (Exception e) {
                        log.error("Ticker WebSocket 메시지 처리 실패", e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("Ticker WebSocket 종료: {}", reason);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("Ticker WebSocket 에러", ex);
                }
            };

            wsClient.connect();
        } catch (Exception e) {
            log.error("Ticker WebSocket 연결 실패", e);
        }
    }

    // 내부에서 단일 캐시 조회
    public TickerResponseByProvider getTicker(String market) {
        return tickerCache.get(market);
    }

    // 모든 캐시 조회
    public Map<String, TickerResponseByProvider> getAllTickers() {
        return tickerCache;
    }
}