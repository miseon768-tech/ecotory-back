package com.example.ecotory.global.webSocket.push;

import com.example.ecotory.global.webSocket.provider.response.OrderbookResponseByProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderbookPushService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 새로운 Orderbook 데이터를 프론트로 push
     * STOMP 구독 경로: /topic/orderbook
     */
    public void pushOrderbook(OrderbookResponseByProvider orderbook) {
        try {
            messagingTemplate.convertAndSend("/topic/orderbook", orderbook);
            log.debug("Orderbook Push 완료: {}", orderbook.getCode());
        } catch (Exception e) {
            log.error("Orderbook Push 실패", e);
        }
    }
}