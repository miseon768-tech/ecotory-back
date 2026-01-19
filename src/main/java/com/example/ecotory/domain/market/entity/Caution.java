package com.example.ecotory.domain.market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Caution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean priceFluctuations; // 가격 급등락 경보
    private Boolean tradingVolumeSoaring; // 거래량 급증 경보
    private Boolean DepositAmountSoaring; // 입금량 급증 경보
    private Boolean GlobalPriceDifferences; // 국내외 가격 차이 경보
    private Boolean ConcentrationOfSmallAccounts; // 소수 계정 집중 거래 경보
}
