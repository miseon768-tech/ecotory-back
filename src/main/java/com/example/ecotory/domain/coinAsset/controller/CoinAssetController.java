package com.example.ecotory.domain.coinAsset.controller;

import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.*;
import com.example.ecotory.domain.coinAsset.service.CoinAssetService;
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

    public ResponseEntity<CoinAssetByTradingPairResponse> coinAssetByTradingPair(@RequestAttribute String subject,
                                                                                 @PathVariable Long tradingPairId) {

        CoinAssetByTradingPairResponse response = coinAssetService.coinAssetByTradingPair(subject, tradingPairId);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "마켓 코드로 자산 검색", description = "사용자의 자산을 마켓 코드로 검색합니다.")
    @GetMapping("/market")
    public ResponseEntity<CoinAssetByMarketResponse> coinAssetByMarket(@RequestAttribute String subject,
                                                                       @RequestParam String market) {

        CoinAssetByMarketResponse response = coinAssetService.coinAssetByMarket(subject, market);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "한글로 자산 검색", description = "사용자의 자산을 한글로 검색합니다.")
    @GetMapping("/korean")
    public ResponseEntity<CoinAssetByKoreanResponse> coinAssetByKorean(@RequestAttribute String subject,
                                                                       @RequestParam String koreanName) {

        CoinAssetByKoreanResponse response = coinAssetService.coinAssetByKorean(subject, koreanName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "영문으로 자산 검색", description = "사용자의 자산을 영문으로 검색합니다.")
    @GetMapping("/english")
    public ResponseEntity<CoinAssetByEnglishResponse> coinAssetByEnglish(@RequestAttribute String subject,
                                                                         @RequestParam String englishName) {

        CoinAssetByEnglishResponse response = coinAssetService.coinAssetByEnglish(subject, englishName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "카테고리로 자산 검색", description = "사용자의 자산을 카테고리로 검색합니다. 마켓, 한글명, 영문명 중 하나 이상의 파라미터를 전달해야 합니다.")
    @GetMapping("/category")
    public ResponseEntity<CoinAssetByCategoryResponse> coinAssetByCategory(@RequestAttribute String subject,
                                                                           @RequestParam(required = false) String market,
                                                                           @RequestParam(required = false) String koreanName,
                                                                           @RequestParam(required = false) String englishName) {

        CoinAssetByCategoryResponse response = coinAssetService.coinAssetByCategory(subject, market, koreanName, englishName);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);

    }


}

