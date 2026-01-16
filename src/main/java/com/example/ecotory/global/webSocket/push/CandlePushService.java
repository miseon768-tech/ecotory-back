package com.example.ecotory.global.webSocket.push;

import com.example.ecotory.global.webSocket.provider.response.CandleResponseByProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandlePushService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 새로운 Candle 데이터를 프론트로 push
     * STOMP 구독 경로: /topic/candle
     */
    public void pushCandle(CandleResponseByProvider candle) {
        try {
            messagingTemplate.convertAndSend("/topic/candle", candle);
            log.debug("Candle Push 완료: {}", candle.getMarket());
        } catch (Exception e) {
            log.error("Candle Push 실패", e);
        }
    }
}