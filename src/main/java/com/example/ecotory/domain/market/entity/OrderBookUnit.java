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
public class OrderBookUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double askPrice;           // 매도 호가
    private Double bidPrice;           // 매수 호가
    private Double askSize;            // 매도 잔량
    private Double bidSize;            // 매수 잔량
    private Double level;               // 해당 호가가 적용된 가격 단위
}

