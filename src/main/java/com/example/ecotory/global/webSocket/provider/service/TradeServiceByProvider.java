package com.example.ecotory.global.webSocket.provider.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import com.example.ecotory.global.webSocket.provider.mapper.CandleMapper;
import com.example.ecotory.global.webSocket.provider.mapper.TradeMapper;
import com.example.ecotory.global.webSocket.provider.response.CandleResponseByProvider;
import com.example.ecotory.global.webSocket.provider.response.TradeResponseByProvider;
import com.example.ecotory.global.webSocket.push.TradePushService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceByProvider {

    private final Map<String, TradeResponseByProvider> tradeCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient wsClient;
    private final TradePushService tradePushService;
    private final TradingPairService tradingPairService;
    private List<String> markets;


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
                    log.info("Trade WebSocket 연결 성공!");
                    String ticket = "{\"ticket\":\"upbit-trade\"}";
                    String subscribe = "{\"type\":\"trade\",\"codes\":" + markets.toString() + "}";
                    String format = "{\"format\":\"DEFAULT\"}";
                    send("[" + ticket + "," + subscribe + "," + format + "]");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        JsonNode json = objectMapper.readTree(message);

                        TradeResponseByProvider trade = TradeMapper.map(json);


                        // key: tradingPair + sequentialId
                        String key = trade.getMarket() + "-" + trade.getSequentialId();
                        tradeCache.put(key, trade);
                        tradePushService.pushTrade(trade);

                    } catch (Exception e) {
                        log.error("WebSocket 메시지 처리 실패", e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("Trade WebSocket 종료: {}", reason);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("Trade WebSocket 에러", ex);
                }
            };

            wsClient.connect();
        } catch (Exception e) {
            log.error("Trade WebSocket 연결 실패", e);
        }
    }

    // 개별 체결 조회
    public TradeResponseByProvider getTrade(String market, long sequentialId) {
        return tradeCache.get(market + "-" + sequentialId);
    }

    // 전체 캐시 조회
    public Map<String, TradeResponseByProvider> getAllTrades() {
        return tradeCache;
    }
}