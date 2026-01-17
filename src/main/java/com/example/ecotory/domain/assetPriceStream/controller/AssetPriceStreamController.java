package com.example.ecotory.domain.assetPriceStream.controller;

import com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary.*;
import com.example.ecotory.domain.assetPriceStream.service.AssetPriceStreamService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "자산 요약 API", description = "자산 요약 조회 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/KRWAssets/summary")
@Validated

public class AssetPriceStreamController {

    private final AssetPriceStreamService assetPriceStreamService;

    // 코인별 평가손익 조회
    @GetMapping("/profit")
    public ResponseEntity<?> getTotalProfit() {

        TotalProfitResponse response = assetPriceStreamService.getTotalProfit();

        return ResponseEntity.ok(response);

    }

    // 총 평가손익 조회
    @GetMapping("/profit")
    public ResponseEntity<?> getTotalProfit() {

        TotalProfitResponse response = assetPriceStreamService.getTotalProfit();

        return ResponseEntity.ok(response);

    }

    // 총 평가금액(KRW) 조회
    @GetMapping("/eval")
    public ResponseEntity<?> getTotalEvalAmount(@RequestAttribute String subject) {

        TotalEvalAmountResponse response = assetPriceStreamService.getTotalEvalAmount();

                return ResponseEntity.ok(response);

    }

    // 총 보유자산 조회
    @GetMapping("/total")
    public ResponseEntity<?> getTotalAssets(@RequestAttribute String subject) {

        TotalAssetsResponse response = assetPriceStreamService.getTotalAssets();

                return ResponseEntity.ok(response);

    }

    // 총 수익률 조회
    @GetMapping("/profit-rate")
    public ResponseEntity<?> getTotalProfitRate() {

        TotalProfitRateResponse response = assetPriceStreamService.getTotalProfitRate();

                return ResponseEntity.ok(response);

    }

}


// 평가 금액 타임라인 조회
// 자산 거래 히스토리 조회
// 보유자산 포트폴리오(%)
// 자산 대시보드 조회(금액별)
