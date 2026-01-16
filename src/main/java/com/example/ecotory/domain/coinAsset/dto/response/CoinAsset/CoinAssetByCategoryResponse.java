package com.example.ecotory.domain.coinAsset.dto.response.CoinAsset;

import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CoinAssetByCategoryResponse {
    private List<CoinAsset> coinAssetList;
    private boolean success;
}
