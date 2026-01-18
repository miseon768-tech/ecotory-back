package com.example.ecotory.domain.assetPriceStream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PortfolioItem {
    private String asset; // 자산명
    private double valuation; // 평가금액
    private double ratio; // 비율
}