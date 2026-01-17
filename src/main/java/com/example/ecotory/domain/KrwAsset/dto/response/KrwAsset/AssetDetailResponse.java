package com.example.ecotory.domain.KrwAsset.dto.response.Krwassets;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetDetailResponse {

    private String market;
    private String koreanName;
    private String englishName;
    private double amount;
    private double avgPrice;
    private boolean success;
}
