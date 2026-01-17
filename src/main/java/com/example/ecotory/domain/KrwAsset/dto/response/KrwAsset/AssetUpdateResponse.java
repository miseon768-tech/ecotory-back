package com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetUpdateResponse {
    private KrwAsset KrwAsset;
    private boolean success;
}
