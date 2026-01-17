package com.example.ecotory.global.webSocket.provider.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import com.example.ecotory.global.webSocket.provider.mapper.CandleMapper;
import com.example.ecotory.global.webSocket.provider.response.CandleResponseByProvider;
import com.example.ecotory.global.webSocket.push.CandlePushService;
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
public class CandleServiceByProvider {

    private final Map<String, CandleResponseByProvider> candleCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient wsClient;
    private final CandlePushService candlePushService;

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
                    log.info("Candle WebSocket 연결 성공!");
                    String ticket = "{\"ticket\":\"upbit-candle\"}";
                    String subscribe = "{\"type\":\"candle.1s\",\"codes\":" + markets.toString() + "}";
                    String format = "{\"format\":\"DEFAULT\"}";
                    send("[" + ticket + "," + subscribe + "," + format + "]");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        JsonNode json = objectMapper.readTree(message);

                        CandleResponseByProvider candle = CandleMapper.map(json);

                        // tradingPair + UTC 기준 시각으로 캐시에 저장
                        String key = candle.getMarket() + "-" + candle.getCandleDateTimeUtc();
                        candleCache.put(key, candle);
                        candlePushService.pushCandle(candle);

                    } catch (Exception e) {
                        log.error("Candle WebSocket 메시지 처리 실패", e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("Candle WebSocket 종료: {}", reason);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("Candle WebSocket 에러", ex);
                }
            };

            wsClient.connect();
        } catch (Exception e) {
            log.error("Candle WebSocket 연결 실패", e);
        }
    }

    // 단일 캔들 조회
    public CandleResponseByProvider getCandle(String market, String candleDateTimeUtc) {
        return candleCache.get(market + "-" + candleDateTimeUtc);
    }

    // 전체 캔들 캐시 조회
    public Map<String, CandleResponseByProvider> getAllCandles() {
        return candleCache;
    }
}