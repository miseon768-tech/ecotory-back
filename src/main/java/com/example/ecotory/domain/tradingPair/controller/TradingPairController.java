package com.example.ecotory.domain.tradingPair.controller;

import com.example.ecotory.domain.tradingPair.dto.response.TradingPairResponse;
import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.service.TradingPairService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class TradingPairController {

    private final TradingPairService tradingPairService;

    @GetMapping("/all")
    @Operation(summary = "페어 목록 조회", description = "DB + Upbit API를 조회하여 모든 마켓(페어) 목록을 가져옵니다.")
    public ResponseEntity<TradingPairResponse> getAllMarkets() {

        List<TradingPair> tradingPairs = tradingPairService.getMarkets();

        TradingPairResponse response = TradingPairResponse.builder()
                .tradingPairs(tradingPairs)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}