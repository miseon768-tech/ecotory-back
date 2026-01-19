package com.example.ecotory.domain.market.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;                 // 거래쌍 코드, 예: KRW-BTC
    private LocalDateTime timestamp;                // 조회 요청 시각 타임스탬프(ms)
    private Double totalAskSize;         // 전체 매도 잔량 합계
    private Double totalBidSize;         // 전체 매수 잔량 합계
    private List<OrderBookUnit> orderBookUnits; // 호가 단위별 정보 리스트
    private Double level;                  // 호가 모아보기 단위 (0: 단위 없음, 1 이상: 지정된 가격 단위)

    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
