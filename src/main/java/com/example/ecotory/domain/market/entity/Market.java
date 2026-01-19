package com.example.ecotory.domain.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Market { // 마켓(페어 목록) 전체 조회

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market; // 페어(거래쌍) 코드
    private String koreanName; // 한글명
    private String englishName; // 영문명

    @OneToOne
    @JoinColumn(name = "market_event_id")
    private MarketEvent marketEvent; // 종목 경보 정보

}