package com.example.ecotory.domain.krwAsset.dto.response.KRWassets;

import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateAssetResponse {
    private KRWAsset KRWAsset;
    private boolean success;
}
