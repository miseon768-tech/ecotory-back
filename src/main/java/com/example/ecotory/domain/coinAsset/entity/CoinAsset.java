package com.example.ecotory.domain.coinAsset.entity;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class CoinAsset {

    @Id
    private String id;

    private String krwAssetId;

    @ManyToOne
    @JoinColumn(name = "trading_pair_id")
    private TradingPair tradingPair;

    private double coinBalance;
    private long avgBuyPrice;
    private long buyAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
