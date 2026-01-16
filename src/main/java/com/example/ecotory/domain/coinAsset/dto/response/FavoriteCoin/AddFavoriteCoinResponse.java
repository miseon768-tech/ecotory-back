package com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin;

import com.example.ecotory.domain.coinAsset.entity.FavoriteCoin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddFavoriteCoinResponse {
    private FavoriteCoin favoriteCoin;
    private boolean success;
}
