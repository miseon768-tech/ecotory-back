package com.example.ecotory.domain.krwAsset.controller;

import com.example.ecotory.domain.krwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.response.KRWassets.*;
import com.example.ecotory.domain.krwAsset.service.KRWAssetService;
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

public class KRWAssetController {
    private final KRWAssetService KRWAssetService;

    @Operation(summary = "자산 추가", description = "사용자의 자산을 추가합니다.")
    @PostMapping
    public ResponseEntity<AddKRWAssetResponse> addAsset(@RequestAttribute String subject,
                                                        @RequestBody AddAssetRequest addAssetRequest) {

        AddKRWAssetResponse response = KRWAssetService.addAsset(subject, addAssetRequest);

        return ResponseEntity.status(HttpStatus.CREATED) //201
                .body(response);
    }

    @Operation(summary = "자산 수정", description = "사용자의 자산을 수정합니다.")
    @PutMapping("/{KRWAssetId}")
    public ResponseEntity<UpdateAssetResponse> updateAsset(@PathVariable String KRWAssetId,
                                                           @RequestAttribute String subject,
                                                           @RequestBody UpdateAssetRequest updateAssetRequest) {

        UpdateAssetResponse response = KRWAssetService.updateAsset(KRWAssetId, subject, updateAssetRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "자산 삭제", description = "사용자의 자산을 삭제합니다.")
    @DeleteMapping("/{KRWAssetId}")
    public ResponseEntity<DeleteAssetResponse> deleteAsset(@PathVariable String KRWAssetId,
                                                           @RequestAttribute String subject) {

        KRWAssetService.deleteAsset(KRWAssetId, subject);

        return ResponseEntity.status(HttpStatus.NO_CONTENT) //204
                .body(null);
    }

    @Operation(summary = "자산 조회", description = "사용자의 자산을 조회합니다.")
    @GetMapping
    public ResponseEntity<KRWAssetByMemberResponse> KRWAssetByMember(@RequestAttribute String subject) {

        KRWAssetByMemberResponse response = KRWAssetService.KRWAssetByMember(subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

}

