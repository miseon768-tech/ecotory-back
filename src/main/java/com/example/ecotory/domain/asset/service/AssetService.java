package com.example.ecotory.domain.asset.service;

import com.example.ecotory.domain.asset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.asset.dto.response.AddAssetResponse;
import com.example.ecotory.domain.asset.dto.response.SearchMarketsResponse;
import com.example.ecotory.domain.asset.entity.Asset;
import com.example.ecotory.domain.asset.repository.AssetRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "자산 서비스", description = "자산 추가, 조회, 수정, 삭제 서비스")
@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    // 자산 추가
    public AddAssetResponse addAsset(AddAssetRequest addAssetRequest) {

        Asset asset = new Asset();
        asset.setMemberId(addAssetRequest.getMemberId());
        asset.setCoinSymbol(addAssetRequest.getCoinSymbol());
        asset.setQuantity(addAssetRequest.getQuantity());
        asset.setPurchasePrice(addAssetRequest.getPurchasePrice());

        assetRepository.save(asset);

        return AddAssetResponse.builder()
                .asset(asset) // 실제로는 저장된 자산 객체를 반환해야 함
                .success(true)
                .build();
    }


    // 마켓 전체 검색 (코인명/심볼 검색)
    public SearchMarketsResponse searchMarkets(String keyword) {
    }


}
