package com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteFavoriteCoinResponse {
    private KrwAsset KRWAsset;
    private boolean success;
}
