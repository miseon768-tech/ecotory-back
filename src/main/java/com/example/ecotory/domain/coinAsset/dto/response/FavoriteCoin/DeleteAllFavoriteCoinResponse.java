package com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin;

import com.example.ecotory.domain.coinAsset.entity.FavoriteCoin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteAllFavoriteCoinResponse {
    private List<FavoriteCoin> favoriteCoinList;
    private boolean success;
}
