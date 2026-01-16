package com.example.ecotory.domain.coinAsset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCoinId implements Serializable {

    private String memberId;
    private Long tradingPairId;
}