package com.example.ecotory.global.webSocket.provider.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import com.example.ecotory.global.webSocket.provider.mapper.OrderbookMapper;
import com.example.ecotory.global.webSocket.provider.response.OrderbookResponseByProvider;
import com.example.ecotory.global.webSocket.push.OrderbookPushService;
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
public class OrderbookServiceByProvider {

    private final Map<String, OrderbookResponseByProvider> orderbookCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderbookPushService orderbookPushService;
    private final TradingPairService tradingPairService;
    private WebSocketClient wsClient;

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
                    log.info("Orderbook WebSocket 연결 성공!");
                    String ticket = "{\"ticket\":\"upbit-orderbook\"}";
                    String subscribe = "{\"type\":\"orderbook\",\"codes\":" + markets.toString() + "}";
                    String format = "{\"format\":\"DEFAULT\"}";
                    send("[" + ticket + "," + subscribe + "," + format + "]");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        JsonNode json = objectMapper.readTree(message);

                        OrderbookResponseByProvider orderbook = OrderbookMapper.map(json, objectMapper);


                        // 마켓+타임스탬프 키로 캐시에 저장
                        String key = orderbook.getCode() + "-" + orderbook.getTimestamp();
                        orderbookCache.put(key, orderbook);
                        orderbookPushService.pushOrderbook(orderbook);

                    } catch (Exception e) {
                        log.error("Orderbook WebSocket 메시지 처리 실패", e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("Orderbook WebSocket 종료: {}", reason);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("Orderbook WebSocket 에러", ex);
                }
            };

            wsClient.connect();
        } catch (Exception e) {
            log.error("Orderbook WebSocket 연결 실패", e);
        }
    }

    // 내부에서 단일 캐시 조회
    public OrderbookResponseByProvider getOrderbook(String market, long timestamp) {
        return orderbookCache.get(market + "-" + timestamp);
    }

    // 모든 캐시 조회
    public Map<String, OrderbookResponseByProvider> getAllOrderbooks() {
        return orderbookCache;
    }
}