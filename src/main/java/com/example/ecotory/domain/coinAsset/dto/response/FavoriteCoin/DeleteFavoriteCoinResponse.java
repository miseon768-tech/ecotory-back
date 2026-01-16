package com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin;

import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteFavoriteCoinResponse {
    private krwAsset KRWAsset;
    private boolean success;
}
