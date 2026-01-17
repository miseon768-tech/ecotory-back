package com.example.ecotory.domain.KrwAsset.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddAssetRequest {

    private long cashBalance;
    private long totalByAmount;

}