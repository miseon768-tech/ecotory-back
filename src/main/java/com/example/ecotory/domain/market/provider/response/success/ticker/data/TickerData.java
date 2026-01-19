package com.example.ecotory.domain.market.provider.response.success.ticker.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TickerData {
    private String market;                  // 페어(거래쌍) 코드, 예: KRW-BTC
    private String tradeDate;               // 최근 체결 일자 (UTC 기준, yyyyMMdd)
    private String tradeTime;               // 최근 체결 시각 (UTC 기준, HHmmss)
    private String tradeDateKst;            // 최근 체결 일자 (한국시간, yyyyMMdd)
    private String tradeTimeKst;            // 최근 체결 시각 (한국시간, HHmmss)
    private Long tradeTimestamp;            // 체결 시각 타임스탬프(ms)
    private Double openingPrice;            // 시가, 당일 첫 거래 가격
    private Double highPrice;               // 고가, 당일 최고 거래 가격
    private Double lowPrice;                // 저가, 당일 최저 거래 가격
    private Double tradePrice;              // 종가, 현재가
    private Double prevClosingPrice;        // 전일 종가 (UTC 0시 기준)
    private String change;                  // 가격 변동 상태 (EVEN: 보합, RISE: 상승, FALL: 하락)
    private Double changePrice;             // 전일 종가 대비 가격 변화 (절대값)
    private Double changeRate;              // 전일 종가 대비 가격 변화율 (절대값)
    private Double signedChangePrice;       // 전일 종가 대비 가격 변화 (부호 포함, 상승:+ 하락:-)
    private Double signedChangeRate;        // 전일 종가 대비 가격 변화율 (부호 포함)
    private Double tradeVolume;             // 최근 거래 수량
    private Double accTradePrice;           // 누적 거래 금액 (UTC 0시 기준)
    private Double accTradePrice24h;        // 24시간 누적 거래 금액
    private Double accTradeVolume;          // 누적 거래량 (UTC 0시 기준)
    private Double accTradeVolume24h;       // 24시간 누적 거래량
    private Double highest52WeekPrice;      // 52주 신고가
    private String highest52WeekDate;       // 52주 신고가 달성일 (yyyy-MM-dd)
    private Double lowest52WeekPrice;       // 52주 신저가
    private String lowest52WeekDate;        // 52주 신저가 달성일 (yyyy-MM-dd)
    private Long timestamp;                 // 현재가 정보 반영 시각 타임스탬프(ms)
}
