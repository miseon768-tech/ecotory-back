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
public class Ticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;                // 페어(거래쌍) 코드, 예: KRW-BTC
    private String tradeDateKst;        // 최근 체결 일자 (한국시간, yyyyMMdd)
    private String tradeTimeKst;        // 최근 체결 시각 (한국시간, HHmmss)
    private LocalDateTime tradeTimestamp;         // 체결 시각 타임스탬프(ms)

    private Double openingPrice;         // 시가, 당일 첫 거래 가격
    private Double highPrice;            // 고가, 당일 최고 거래 가격
    private Double lowPrice;             // 저가, 당일 최저 거래 가격
    private Double tradePrice;           // 종가, 현재가

    private String change;                // 가격 변동 상태 (EVEN: 보합, RISE: 상승, FALL: 하락)
    private Double changePrice;          // 전일 종가 대비 가격 변화 (절대값)
    private Double changeRate;           // 전일 종가 대비 가격 변화율 (절대값)
    private Double signedChangePrice;   // 전일 종가 대비 가격 변화 (부호 포함, 상승:+ 하락:-)
    private Double signedChangeRate;    // 전일 종가 대비 가격 변화율 (부호 포함)

    private Double tradeVolume;          // 최근 거래 수량
    private Double accTradePrice24h;   // 24시간 누적 거래 금액
    private Double accTradeVolume24h;  // 24시간 누적 거래량
    private Double highest52WeekPrice; // 52주 신고가
    private String highest52WeekDate;  // 52주 신고가 달성일 (yyyy-MM-dd)
    private Double lowest52WeekPrice;  // 52주 신저가
    private String lowest52WeekDate;   // 52주 신저가 달성일 (yyyy-MM-dd)
    private LocalDateTime timestamp;               // 현재가 정보 반영 시각 타임스탬프(ms)


    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
