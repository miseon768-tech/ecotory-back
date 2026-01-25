package com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetDeleteResponse {

    private boolean success;

    public static AssetDeleteResponse fromEntity(boolean success) {
        return AssetDeleteResponse.builder()
                .success(success)
                .build();
    }
}
