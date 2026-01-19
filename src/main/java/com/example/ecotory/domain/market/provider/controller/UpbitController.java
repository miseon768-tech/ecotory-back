package com.example.ecotory.domain.market.provider.controller;

import com.example.ecotory.domain.market.provider.response.success.candle.*;
import com.example.ecotory.domain.market.provider.response.success.orderbook.OrderBookInstrumentsResponse;
import com.example.ecotory.domain.market.provider.response.success.orderbook.OrderBookResponse;
import com.example.ecotory.domain.market.provider.response.success.ticker.TickerAllResponse;
import com.example.ecotory.domain.market.provider.response.success.ticker.TickerResponse;
import com.example.ecotory.domain.market.provider.response.success.trade.TradesTicksResponse;
import com.example.ecotory.domain.market.provider.response.success.tradingPairs.AllPairsResponse;
import com.example.ecotory.domain.market.provider.service.UpbitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UpbitController {

    private final UpbitService upbitService;

    // 1. 페어 목록 조회
    @GetMapping("/market/all")
    public ResponseEntity<List<AllPairsResponse>> markets(@RequestParam(name = "is_details") Boolean isDetails) throws IOException {
        return ResponseEntity.ok(upbitService.getMarkets(isDetails));
    }

    // 2. 초(Second) 캔들 조회
    @GetMapping("/candles/seconds")
    public ResponseEntity<List<SecondsCandlesResponse>> seconds(@RequestParam String market,
                                                                @RequestParam(required = false) String to,
                                                                @RequestParam(required = false, defaultValue = "1") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getSecondCandles(market, to, count));
    }

    // 3. 분(Minute) 캔들 조회
    @GetMapping("/candles/minutes/{unit}")
    public ResponseEntity<List<MinutesCandlesResponse>> minutes(@PathVariable Integer unit,
                                                                @RequestParam String market,
                                                                @RequestParam(required = false) String to,
                                                                @RequestParam(required = false, defaultValue = "1") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getMinuteCandles(unit, market, to, count));
    }

    // 4. 일 캔들
    @GetMapping("/candles/days")
    public ResponseEntity<List<DaysCandlesResponse>> days(@RequestParam String market,
                                                          @RequestParam(required = false) String to,
                                                          @RequestParam(required = false, defaultValue = "1") Integer count,
                                                          @RequestParam(name = "converting_price_unit", required = false) String convertingPriceUnit) throws IOException {
        return ResponseEntity.ok(upbitService.getDayCandles(market, to, count, convertingPriceUnit));
    }

    // 5. 주 캔들
    @GetMapping("/candles/weeks")
    public ResponseEntity<List<WeeksCandlesResponse>> weeks(@RequestParam String market,
                                                            @RequestParam(required = false) String to,
                                                            @RequestParam(required = false, defaultValue = "1") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getWeekCandles(market, to, count));
    }

    // 6. 월 캔들
    @GetMapping("/candles/months")
    public ResponseEntity<List<MonthsCandlesResponse>> months(@RequestParam String market,
                                                              @RequestParam(required = false) String to,
                                                              @RequestParam(required = false, defaultValue = "1") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getMonthCandles(market, to, count));
    }

    // 7. 년 캔들
    @GetMapping("/candles/years")
    public ResponseEntity<List<YearsCandlesResponse>> years(@RequestParam String market,
                                                            @RequestParam(required = false) String to,
                                                            @RequestParam(required = false, defaultValue = "1") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getYearCandles(market, to, count));
    }

    // 8. 체결 내역
    @GetMapping("/trades/ticks")
    public ResponseEntity<List<TradesTicksResponse>> trades(@RequestParam String market,
                                                            @RequestParam(required = false) String to,
                                                            @RequestParam(required = false, defaultValue = "1") Integer count,
                                                            @RequestParam(required = false) String cursor,
                                                            @RequestParam(name = "days_ago", required = false, defaultValue = "0") Integer daysAgo) throws IOException {
        return ResponseEntity.ok(upbitService.getTrades(market, to, count, cursor, daysAgo));
    }

    // 9. 페어 단위 현재가
    @GetMapping("/ticker")
    public ResponseEntity<List<TickerResponse>> ticker(@RequestParam String market) throws IOException {
        return ResponseEntity.ok(upbitService.getTicker(market));
    }

    // 10. 전체 현재가
    @GetMapping("/ticker/all")
    public ResponseEntity<List<TickerAllResponse>> marketTicker(@RequestParam(name = "quote_currencies") String quoteCurrencies) throws IOException {
        return ResponseEntity.ok(upbitService.getMarketTicker(quoteCurrencies));
    }

    // 11. 호가
    @GetMapping("/orderbook")
    public ResponseEntity<List<OrderBookResponse>> orderbook(@RequestParam String market,
                                                             @RequestParam(required = false, defaultValue = "0") String level,
                                                             @RequestParam(required = false, defaultValue = "30") Integer count) throws IOException {
        return ResponseEntity.ok(upbitService.getOrderbook(market, level, count));
    }

    // 12. 호가 정책
    @GetMapping("/orderbook/instruments")
    public ResponseEntity<List<OrderBookInstrumentsResponse>> orderbookPolicy(@RequestParam String market) throws IOException {
        return ResponseEntity.ok(upbitService.getOrderbookPolicy(market));
    }
}