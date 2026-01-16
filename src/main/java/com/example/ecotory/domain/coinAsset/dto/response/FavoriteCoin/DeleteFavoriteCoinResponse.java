package com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin;

import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteFavoriteCoinResponse {
    private KRWAsset KRWAsset;
    private boolean success;
}
