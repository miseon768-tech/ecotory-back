package com.example.ecotory.domain.coinAsset.controller;

import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.CoinBuyAmountUpsertResponse;
import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.*;
import com.example.ecotory.domain.coinAsset.service.CoinAssetService;
import com.example.ecotory.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Coin Asset", description = "코인 자산 관련 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/coin/assets")

public class CoinAssetController {
    private final CoinAssetService coinAssetService;

    @Operation(summary = "트레이딩 페어 ID로 자산 검색", description = "사용자의 자산을 트레이딩 페어 ID로 검색합니다.")
    @GetMapping("/{tradingPairId}")

    public ResponseEntity<CoinAssetByTradingPairResponse> coinAssetByTradingPair(@RequestAttribute Member member,
                                                                                 @PathVariable Long tradingPairId) {

        CoinAssetByTradingPairResponse response = coinAssetService.coinAssetByTradingPair(member, tradingPairId);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "마켓 코드로 자산 검색", description = "사용자의 자산을 마켓 코드로 검색합니다.")
    @GetMapping("/market")
    public ResponseEntity<CoinAssetByMarketResponse> coinAssetByMarket(@RequestAttribute Member member,
                                                                       @RequestParam String market) {

        CoinAssetByMarketResponse response = coinAssetService.coinAssetByMarket(member, market);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "한글로 자산 검색", description = "사용자의 자산을 한글로 검색합니다.")
    @GetMapping("/korean")
    public ResponseEntity<CoinAssetByKoreanResponse> coinAssetByKorean(@RequestAttribute Member member,
                                                                       @RequestParam String koreanName) {

        CoinAssetByKoreanResponse response = coinAssetService.coinAssetByKorean(member, koreanName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "영문으로 자산 검색", description = "사용자의 자산을 영문으로 검색합니다.")
    @GetMapping("/english")
    public ResponseEntity<CoinAssetByEnglishResponse> coinAssetByEnglish(@RequestAttribute Member member,
                                                                         @RequestParam String englishName) {

        CoinAssetByEnglishResponse response = coinAssetService.coinAssetByEnglish(member, englishName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "카테고리로 자산 검색", description = "사용자의 자산을 카테고리로 검색합니다. 마켓, 한글명, 영문명 중 하나 이상의 파라미터를 전달해야 합니다.")
    @GetMapping("/category")
    public ResponseEntity<CoinAssetByCategoryResponse> coinAssetByCategory(@RequestAttribute Member member,
                                                                           @RequestParam(required = false) String market,
                                                                           @RequestParam(required = false) String koreanName,
                                                                           @RequestParam(required = false) String englishName) {

        CoinAssetByCategoryResponse response = coinAssetService.coinAssetByCategory(member, market, koreanName, englishName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);

    }

    @Operation(summary = "코인별 매수금액 입력 및 수정", description = "사용자의 코인별 매수금액을 입력 및 수정합니다.")
    @PostMapping("/purchase-by-coin")
    public ResponseEntity<CoinBuyAmountUpsertResponse> coinBuyAmountUpsert(@RequestAttribute Member member,
                                                                           @RequestBody Long amount) {

        long coinBuyAmountUpsert = coinAssetService.coinBuyAmountUpsert(member, amount);
        CoinBuyAmountUpsertResponse response = CoinBuyAmountUpsertResponse.builder()
                .success(true)
                .coinBuyAmountUpsert(coinBuyAmountUpsert)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "코인별 매수금액 조회", description = "사용자의 코인별 매수금액을 조회합니다.")
    @GetMapping("/purchase-by-coin")
    public ResponseEntity<CoinBuyAmountGetResponse> coinBuyAmountGet(@RequestAttribute Member member) {

        long coinBuyAmountGet = coinAssetService.coinBuyAmountGet(member);

        CoinBuyAmountGetResponse response = CoinBuyAmountGetResponse.builder()
                .success(true)
                .coinBuyAmountGet(coinBuyAmountGet)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "총 매수금액 조회", description = "사용자의 총 매수금액을 조회합니다.")
    @GetMapping("/total-purchase-amount")
    public ResponseEntity<TotalCoinBuyAmountResponse> totalCoinBuyAmount(@RequestAttribute Member member) {

        long totalBuyAmount = coinAssetService.totalCoinBuyAmount(member);

        TotalCoinBuyAmountResponse response = TotalCoinBuyAmountResponse.builder()
                .success(true)
                .totalBuyAmount(totalBuyAmount)
                .build();

        return ResponseEntity.ok(response);
    }

}
