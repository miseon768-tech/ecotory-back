package com.example.ecotory.domain.coinAsset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_coin")
@IdClass(FavoriteCoinId.class)
@Getter
@Setter
@NoArgsConstructor
public class FavoriteCoin {

    @Id
    private String memberId;

    @Id
    private Long tradingPairId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}