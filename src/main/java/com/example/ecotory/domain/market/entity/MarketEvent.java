package com.example.ecotory.domain.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MarketEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean warning; // 유의 종목 여부

    @OneToOne
    @JoinColumn(name = "caution_id")
    private Caution caution; // 주의 종목 정보
}