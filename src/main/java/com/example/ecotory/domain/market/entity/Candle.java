package com.example.ecotory.domain.market.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Candle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;                 // 조회 대상 거래쌍 코드, 예: KRW-BTC
    private String candleDateTimeKst;   // 캔들 구간 시작 시각 (KST 기준), 형식: yyyy-MM-dd'T'HH:mm:ss
    private Double openingPrice;          // 시가, 해당 캔들의 첫 거래 가격
    private Double highPrice;             // 고가, 해당 캔들의 최고 거래 가격
    private Double lowPrice;              // 저가, 해당 캔들의 최저 거래 가격
    private Double tradePrice;            // 종가, 해당 캔들의 마지막 거래 가격
    private Double candleAccTradePrice; // 캔들 동안의 누적 거래 금액
    private Double candleAccTradeVolume;// 캔들 동안 누적 거래된 디지털 자산 수량
    private LocalDateTime timestamp;                // 해당 캔들의 마지막 틱이 저장된 시각 타임스탬프(ms)

    // days
    private Double prevClosingPrice;       // 전일 종가
    private Double changePrice;             // 전일 종가 대비 가격 변화
    private Double changeRate;              // 전일 종가 대비 가격 변화율
    private Double convertedTradePrice;    // 선택적 필드, 환산 종가 (요청 시 존재, 없을 수도 있음)

    // minutes, seconds
    private Integer unit;                       // 캔들 집계 시간 단위(분), 예: 1, 3, 5, 10, 15, 30, 60, 240

    // months, weeks, years
    private String firstDayOfPeriod;      // 해당 월의 첫 날 (yyyy-MM-dd)




    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
