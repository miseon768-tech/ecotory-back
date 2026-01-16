package com.example.ecotory.domain.krwAsset.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddAssetRequest {

    private double cashBalance;
    private double totalByAmount;

}