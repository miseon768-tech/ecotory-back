package com.example.ecotory.domain.KrwAsset.controller;

import com.example.ecotory.domain.KrwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset.*;
import com.example.ecotory.domain.KrwAsset.service.KrwAssetService;
import com.example.ecotory.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "자산 API", description = "자산 추가, 조회 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/assets")

public class KrwAssetController {
    private final KrwAssetService krwAssetService;

    @Operation(summary = "자산 추가", description = "사용자의 자산을 추가합니다.")
    @PostMapping
    public ResponseEntity<KrwAssetCreateResponse> addAsset(@RequestAttribute Member member,
                                                           @RequestBody AddAssetRequest addAssetRequest) {

        KrwAssetCreateResponse response = krwAssetService.addAsset(member, addAssetRequest);

        return ResponseEntity.status(HttpStatus.CREATED) //201
                .body(response);
    }

    @Operation(summary = "자산 수정", description = "사용자의 자산을 수정합니다.")
    @PutMapping("/{KrwAssetId}")
    public ResponseEntity<AssetUpdateResponse> updateAsset(@PathVariable String KrwAssetId,
                                                           @RequestAttribute Member member,
                                                           @RequestBody UpdateAssetRequest updateAssetRequest) {

        AssetUpdateResponse response = krwAssetService.updateAsset(KrwAssetId, member, updateAssetRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "자산 삭제", description = "사용자의 자산을 삭제합니다.")
    @DeleteMapping("/{KrwAssetId}")
    public ResponseEntity<AssetDeleteResponse> deleteAsset(@PathVariable String KRWAssetId,
                                                           @RequestAttribute Member member) {

        krwAssetService.deleteAsset(KRWAssetId, member);

        return ResponseEntity.status(HttpStatus.NO_CONTENT) //204
                .body(null);
    }

    @Operation(summary = "자산 조회", description = "사용자의 자산을 조회합니다.")
    @GetMapping
    public ResponseEntity<KrwAssetByMemberResponse> KRWAssetByMember(@RequestAttribute Member member) {

        KrwAssetByMemberResponse response = krwAssetService.KRWAssetByMember(member);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "주문 가능 금액 입력 및 수정", description = "사용자의 주문 가능 금액을 입력 및 수정합니다.")
    @PostMapping("/available-order-amount")
    public ResponseEntity<CashBalanceUpsertResponse> upsertCashBalance(@RequestAttribute Member member,
                                                                       @RequestBody Long amount) {
        long upsertCashBalance = krwAssetService.upsertCashBalance(member, amount);

        CashBalanceUpsertResponse response = CashBalanceUpsertResponse.builder()
                .cashBalance(upsertCashBalance)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }


    @Operation(summary = "주문 가능 금액 조회", description = "사용자의 주문 가능 금액을 조회합니다.")
    @GetMapping("/available-order-amount")
    public ResponseEntity<CashBalanceGetResponse> getCashBalance(@RequestAttribute Member member) {

        long getCashBalance = krwAssetService.getCashBalance(member);
        CashBalanceGetResponse response = CashBalanceGetResponse.builder()
                .cashBalance(getCashBalance)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }
}
