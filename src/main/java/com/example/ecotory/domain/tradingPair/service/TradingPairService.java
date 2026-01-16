package com.example.ecotory.domain.tradingPair.service;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.repository.TradingPairRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradingPairService {

    private final TradingPairRepository tradingPairRepository;
    private final ObjectMapper objectMapper;
    private final OkHttpClient client = new OkHttpClient();

    // Upbit API 호출
    private String callUpbitApi(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Upbit API 호출 실패 : " + response.code());
            }
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("Upbit API 호출 실패", e);
        }
    }

    // Upbit에서 마켓 전체 가져오기
    public List<TradingPair> fetchAllMarketsFromUpbit() {
        try {
            String json = callUpbitApi("https://api.upbit.com/v1/market/all");
            return objectMapper.readValue(json, new TypeReference<List<TradingPair>>() {});
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }

    // DB에 저장 및 조회
    public List<TradingPair> getMarkets() {
        // 1. DB에서 기존 마켓 조회
        List<TradingPair> dbTradingPairs = tradingPairRepository.findAll();

        // 2. Upbit API 호출
        List<TradingPair> upbitTradingPairs = fetchAllMarketsFromUpbit();

        // 3. DB에 없는 새로운 마켓 저장
        List<TradingPair> newTradingPairs = upbitTradingPairs.stream()
                .filter(m -> dbTradingPairs.stream()
                        .noneMatch(db -> db.getMarket().equals(m.getMarket())))
                .collect(Collectors.toList());

        if (!newTradingPairs.isEmpty()) {
            tradingPairRepository.saveAll(newTradingPairs);
            dbTradingPairs.addAll(newTradingPairs);
        }

        // 4. KRW 마켓만 필터링
        return dbTradingPairs.stream()
                .filter(m -> m.getMarket().startsWith("KRW-"))
                .collect(Collectors.toList());
    }
}