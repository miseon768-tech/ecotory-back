package com.example.ecotory.domain.assetPriceStream.response;

import com.example.ecotory.domain.assetPriceStream.model.PortfolioItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PortfolioAssetResponse {
    private boolean success;
    private List<PortfolioItem> portfolioItemList;  // 여기서 리스트를 받음
}