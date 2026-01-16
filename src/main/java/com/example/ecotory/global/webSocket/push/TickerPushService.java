package com.example.ecotory.global.webSocket.push;

import com.example.ecotory.global.webSocket.provider.response.TickerResponseByProvider;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TickerPushService {

    private final SimpMessagingTemplate messagingTemplate;

    public TickerPushService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void pushTicker(TickerResponseByProvider ticker) {
        // /topic/ticker로 프론트에 전송
        messagingTemplate.convertAndSend("/topic/ticker", ticker);
    }
}