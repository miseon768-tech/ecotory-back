package com.example.ecotory.domain.KrwAsset.dto.request;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddAssetRequest {

    private long cashBalance;
    private long totalByAmount;

    public KrwAsset toEntity() {
        return KrwAsset.builder()
                .cashBalance(this.cashBalance)
                .totalByAmount(this.totalByAmount)
                .build();
    }

}