package com.example.ecotory.global.webSocket.price;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MarketPriceCache {

    // 키: 마켓이름, 값: 최신가
    private final Map<String, Double> prices = new ConcurrentHashMap<>();

    // 최신가 업데이트
    public void update(String market, double price) {
        prices.put(market, price);
    }

    // 최신가 조회
    public Double get(String market) {
        return prices.get(market);
    }
}