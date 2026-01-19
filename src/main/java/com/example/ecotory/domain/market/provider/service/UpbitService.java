package com.example.ecotory.domain.market.provider.service;

import com.example.ecotory.domain.market.provider.response.success.candle.*;
import com.example.ecotory.domain.market.provider.response.success.orderbook.OrderBookInstrumentsResponse;
import com.example.ecotory.domain.market.provider.response.success.orderbook.OrderBookResponse;
import com.example.ecotory.domain.market.provider.response.success.ticker.TickerAllResponse;
import com.example.ecotory.domain.market.provider.response.success.ticker.TickerResponse;
import com.example.ecotory.domain.market.provider.response.success.trade.TradesTicksResponse;
import com.example.ecotory.domain.market.provider.response.success.tradingPairs.AllPairsResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class UpbitService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    // Upbit API 호출 공통 메서드
    private String callUpbitApi(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Upbit API Error: " + response.code());
            }

            return response.body().string();
        }
    }

    // 1. 페어 목록 조회
    public List<AllPairsResponse> getMarkets(Boolean isDetails) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/market/all"
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<AllPairsResponse>>() {
                }
        );
    }

    // 2. 초 캔들
    public List<SecondsCandlesResponse> getSecondCandles(String market, String to, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/seconds?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<SecondsCandlesResponse>>() {
                }
        );
    }

    // 3. 분 캔들
    public List<MinutesCandlesResponse> getMinuteCandles(Integer unit, String market, String to, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/minutes/" + unit + "?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<MinutesCandlesResponse>>() {
                }
        );
    }

    // 4. 일 캔들
    public List<DaysCandlesResponse> getDayCandles(String market, String to, Integer count, String convertingPriceUnit) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/days?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<DaysCandlesResponse>>() {
                }
        );
    }

    // 5. 주 캔들
    public List<WeeksCandlesResponse> getWeekCandles(String market, String to, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/weeks?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<WeeksCandlesResponse>>() {
                }
        );
    }

    // 6. 월 캔들
    public List<MonthsCandlesResponse> getMonthCandles(String market, String to, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/months?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<MonthsCandlesResponse>>() {
                }
        );
    }

    // 7. 년 캔들
    public List<YearsCandlesResponse> getYearCandles(String market, String to, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/candles/years?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<YearsCandlesResponse>>() {
                }
        );
    }

    // 8. 체결 내역
    public List<TradesTicksResponse> getTrades(String market, String to, Integer count, String cursor, Integer daysAgo) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/trades/ticks?market=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<TradesTicksResponse>>() {
                }
        );
    }

    // 9. 페어 단위 현재가
    public List<TickerResponse> getTicker(String market) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/ticker?markets=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<TickerResponse>>() {
                }
        );
    }

    // 10. 전체 현재가
    public List<TickerAllResponse> getMarketTicker(String quoteCurrencies) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/ticker/all"
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<TickerAllResponse>>() {
                }
        );
    }

    // 11. 호가
    public List<OrderBookResponse> getOrderbook(String market, String level, Integer count) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/orderbook?markets=" + market
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<OrderBookResponse>>() {
                }
        );
    }

    // 12. 호가 정책
    public List<OrderBookInstrumentsResponse> getOrderbookPolicy(String market) throws IOException {

        String json = callUpbitApi(
                "https://api.upbit.com/v1/orderbook/instruments"
        );

        return objectMapper.readValue(
                json,
                new TypeReference<List<OrderBookInstrumentsResponse>>() {
                }
        );
    }
}