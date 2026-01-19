package com.example.ecotory.domain.asset.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddAssetRequest {

    private String market;
    private Double quantity;
    private Double avgPrice;
}
