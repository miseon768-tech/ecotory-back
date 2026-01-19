package com.example.ecotory.domain.asset.controller;

import com.example.ecotory.domain.asset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.asset.dto.response.AddAssetResponse;
import com.example.ecotory.domain.asset.dto.response.SearchMarketsResponse;
import com.example.ecotory.domain.asset.service.AssetService;
import com.example.ecotory.domain.member.dto.response.MemberResponse;
import com.example.ecotory.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "자산 API", description = "자산 추가, 조회, 수정, 삭제 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")

public class AssetController {
    private final AssetService assetService;
    private final MemberService memberService;

    // 자산 추가
    @PostMapping("/asset")
    public ResponseEntity<AddAssetResponse> addAsset(@RequestBody AddAssetRequest addAssetRequest) {

        AddAssetResponse addAssetResponse = assetService.addAsset(addAssetRequest);

        AddAssetResponse response = AddAssetResponse.builder()
                .asset(addAssetResponse.getAsset())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED) //201
                .body(response);
    }

    //    마켓 전체 검색 (코인명/심볼 검색)
    @GetMapping("/markets/search")
    public ResponseEntity<SearchMarketsResponse> searchMarkets(@RequestParam String keyword) {

        SearchMarketsResponse searchMarketsResponse = assetService.searchMarkets(keyword);

        SearchMarketsResponse response = SearchMarketsResponse.builder()
                .asset(searchMarketsResponse.getAsset())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);

    }

    //    코인별 내 자산 검색 (코인명/심볼)
    @GetMapping("/assets/search")
    public ResponseEntity<SearchMarketsResponse> searchMyAssets(@RequestParam String keyword) {
        
        SearchMarketsResponse searchMarketsResponse = assetService.searchMyAssets(keyword);

        SearchMarketsResponse response = SearchMarketsResponse.builder()
                .asset(searchMarketsResponse.getAsset())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }
    //    총 매수금액(KRW) 조회
    //    총 평가금액(KRW) 조회
    //    주문 가능 금액 조회
    //    총 보유자산 조회
    //    전체 평가손익 조회
    //    전체 수익률 조회
    //    보유자산 포트폴리오(%)
    //    특정 코인 자산 상세 조회
    //    자산 거래 히스토리 조회
    //    평가금액 타임라인 조회
    //    자산 알림 등록
    //    알림 목록 조회
    //    알림 삭제
    //    관심 코인 등록
    //    관심 코인 목록 조회
    //    관심 코인 해제
    //    자산 대시보드 조회
    //    자산 스냅샷 조회
    //    자산 리스크 분석
}

