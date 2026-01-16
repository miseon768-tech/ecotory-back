package com.example.ecotory.global.webSocket.push;

import com.example.ecotory.global.webSocket.provider.response.TradeResponseByProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradePushService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 새로운 체결(trade) 데이터를 프론트로 Push
     * STOMP 구독 경로: /topic/trade
     */
    public void pushTrade(TradeResponseByProvider trade) {
        try {
            messagingTemplate.convertAndSend("/topic/trade", trade);
            log.debug("Trade Push 완료: {}", trade.getMarket());
        } catch (Exception e) {
            log.error("Trade Push 실패", e);
        }
    }
}