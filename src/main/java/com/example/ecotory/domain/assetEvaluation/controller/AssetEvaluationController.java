package com.example.ecotory.domain.assetEvaluation.controller;

import com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary.*;
import com.example.ecotory.domain.krwAsset.service.KRWAssetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
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

public class AssetEvaluationController {

    final KRWAssetsService krwAssetsService;

    //    주문 가능 금액 조회
    @Operation(summary = "주문 가능 금액 조회", description = "사용자가 입력한 금액을 보유 KRW 잔고에 더한 후, 주문 가능 금액을 조회합니다.")
    @PostMapping("/available")
    public ResponseEntity<?> getAvailableAmount(@RequestAttribute String subject,
                                                @RequestParam @Positive Double amount) {

        CashBalanceResponse response = krwAssetsService.getAvailableAmount(subject, amount);

        return ResponseEntity.ok(response);
    }


    //    총 매수금액(KRW) 조회
    @GetMapping("/buy")
    public ResponseEntity<?> TotalBuyAmount(@RequestAttribute String subject) {

        TotalBuyAmountResponse response = krwAssetsService.TotalBuyAmount(subject);

        return ResponseEntity.ok(response);

    }
/*
    //    총 평가금액(KRW) 조회
    @GetMapping("/eval")
    public ResponseEntity<?> getTotalEvalAmount(@RequestAttribute String subject) {

        TotalEvalAmountResponse response = krwAssetsService.getTotalEvalAmount();

                return ResponseEntity.ok(response);

    }



    //    총 보유자산 조회
    @GetMapping("/total")
    public ResponseEntity<?> getTotalAssets(@RequestAttribute String subject) {

        TotalAssetsResponse response = krwAssetsService.getTotalAssets();

                return ResponseEntity.ok(response);

    }

    //    전체 평가손익 조회
    @GetMapping("/profit")
    public ResponseEntity<?> getTotalProfit() {

        TotalProfitResponse response = krwAssetsService.getTotalProfit();

                return ResponseEntity.ok(response);

    }

    //    전체 수익률 조회
    @GetMapping("/profit-rate")
    public ResponseEntity<?> getTotalProfitRate() {

        TotalProfitRateResponse response = krwAssetsService.getTotalProfitRate();

                return ResponseEntity.ok(response);

    }

    // 평가 금액 타임라인 조회
    @GetMapping("/timeline")
    public ResponseEntity<?> getEvalAmountTimeline() {

        EvalAmountTimelineResponse response = krwAssetsService.getEvalAmountTimeline();

                return ResponseEntity.ok(response);

    }*/
}

