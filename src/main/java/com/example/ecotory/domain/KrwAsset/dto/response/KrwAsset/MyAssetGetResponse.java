package com.example.ecotory.domain.KrwAsset.dto.response.Krwassets;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyAssetGetResponse {
    private KrwAsset KrwAsset;
    private boolean success;
}
