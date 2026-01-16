package com.example.ecotory.domain.tradingPair.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TradingPair { // 마켓(페어 목록) 전체 조회

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market; // 예: BTC-USDT
    private String koreanName; // 한글명
    private String englishName; // 영문명

}