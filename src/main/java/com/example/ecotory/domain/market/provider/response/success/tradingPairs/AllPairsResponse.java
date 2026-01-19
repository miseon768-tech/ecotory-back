package com.example.ecotory.domain.market.provider.response.success.tradingPairs;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllPairsResponse { // 페어 목록 조회

    private String market; // 페어(거래쌍) 코드
    private String koreanName; // 한글명
    private String englishName; // 영문명
    private MarketEvent marketEvent; // 종목 경보 정보

}