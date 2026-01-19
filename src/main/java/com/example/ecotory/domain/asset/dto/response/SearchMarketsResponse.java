package com.example.ecotory.domain.asset.dto.response;

import com.example.ecotory.domain.asset.entity.Asset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchMarketsResponse {
    private Asset asset;
    private boolean success;
}
