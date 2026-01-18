package com.example.ecotory.domain.assetPriceStream.controller;

import com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary.*;
import com.example.ecotory.domain.assetPriceStream.model.PortfolioItem;
import com.example.ecotory.domain.assetPriceStream.response.CoinEvalAmountResponse;
import com.example.ecotory.domain.assetPriceStream.response.CoinProfitResponse;
import com.example.ecotory.domain.assetPriceStream.response.PortfolioAssetResponse;
import com.example.ecotory.domain.assetPriceStream.service.AssetPriceStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Asset Price Stream", description = "자산 평가금액 스트림 관련 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/KRWAssets/summary")
@Validated

public class AssetPriceStreamController {

    private final AssetPriceStreamService assetPriceStreamService;

    @Operation(summary = "코인별 평가손익 조회", description = "특정 코인의 평가손익을 조회합니다.")
    @GetMapping("/profit")
    public ResponseEntity<CoinProfitResponse> coinProfit(@RequestAttribute String subject,
                                                             @RequestParam String market) {

        double coinProfit = assetPriceStreamService.coinProfit(subject, market);

        CoinProfitResponse response = CoinProfitResponse.builder()
                .success(true)
                .profit(coinProfit)
                .build();

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "총 평가손익 조회", description = "총 평가손익을 조회합니다.")
    @GetMapping("/profit")
    public ResponseEntity<TotalProfitResponse> getTotalProfit(@RequestAttribute String subject) {

        double getTotalProfit = assetPriceStreamService.getTotalProfit(subject);

        TotalProfitResponse response = TotalProfitResponse.builder()
                .success(true)
                .totalProfit(getTotalProfit)
                .build();

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "코인별 평가금액 조회", description = "특정 코인의 평가금액을 조회합니다.")
    @GetMapping("/eval-amount")
    public ResponseEntity<CoinEvalAmountResponse> coinEvalAmount(@RequestAttribute String subject,
                                                                 @RequestParam String market) {

        double coinProfit = assetPriceStreamService.coinEvalAmount(subject, market);

        CoinEvalAmountResponse response = CoinEvalAmountResponse.builder()
                .success(true)
                .evalAmount(coinProfit)
                .build();

        return ResponseEntity.ok(response);

    }
    @Operation(summary = "총 평가 금액 조회", description = "총 평가 금액을 조회합니다.")
    @GetMapping("/total-eval-amount")
    public ResponseEntity<TotalEvalAmountResponse> getTotalEvalAmount(@RequestAttribute String subject) {

        double getTotalEvalAmount = assetPriceStreamService.getTotalEvalAmount(subject);

        TotalEvalAmountResponse response = TotalEvalAmountResponse.builder()
                .success(true)
                .totalEvalAmount(getTotalEvalAmount)
                .build();

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "총 자산 조회", description = "총 자산을 조회합니다.")
    @GetMapping("/total")
    public ResponseEntity<TotalAssetsResponse> getTotalAssets(@RequestAttribute String subject) {

        double getTotalAssets = assetPriceStreamService.getTotalAssets(subject);

        TotalAssetsResponse response = TotalAssetsResponse.builder()
                .success(true)
                .totalAssets(getTotalAssets)
                .build();

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "총 수익률 조회", description = "총 수익률을 조회합니다.")
    @GetMapping("/profit-rate")
    public ResponseEntity<TotalProfitRateResponse> getTotalProfitRate(@RequestAttribute String subject) {

        double getTotalProfitRate = assetPriceStreamService.getTotalProfitRate(subject);

        TotalProfitRateResponse response = TotalProfitRateResponse.builder()
                .success(true)
                .totalProfitRate(getTotalProfitRate)
                .build();

        return ResponseEntity.ok(response);
    }

    // 보유자산 포트폴리오(%)
    @Operation(summary = "보유자산 포트폴리오 조회", description = "보유자산 포트폴리오를 조회합니다.")
    @GetMapping("/portfolio")
    public ResponseEntity<PortfolioAssetResponse> portfolioAsset(@RequestAttribute String subject) {

        List<PortfolioItem> portfolio = assetPriceStreamService.portfolioAsset(subject);

        PortfolioAssetResponse response = PortfolioAssetResponse.builder()
                .success(true)
                .portfolioItemList(portfolio)
                .build();

        return ResponseEntity.ok(response);
    }
}




