package com.example.ecotory.global.webSocket.provider.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import com.example.ecotory.global.webSocket.provider.mapper.TickerMapper;
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
            // tradingPairService에서 KRW 마켓 리스트 가져오기
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

                        TickerResponseByProvider ticker = TickerMapper.map(json);

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