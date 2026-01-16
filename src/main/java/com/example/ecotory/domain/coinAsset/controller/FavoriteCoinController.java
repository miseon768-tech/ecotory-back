package com.example.ecotory.domain.coinAsset.controller;

import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.AddFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.DeleteAllFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.DeleteFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.GetFavoriteCoinsResponse;
import com.example.ecotory.domain.coinAsset.service.FavoriteCoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "관심 자산 API", description = "관심 자산 추가, 조회, 삭제 API")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/assets/favorites")

public class FavoriteCoinController {

    final FavoriteCoinService favoriteCoinService;

    @Operation(summary = "관심 코인 추가", description = "사용자가 관심있는 코인을 추가합니다.")
    @PostMapping
    public ResponseEntity<AddFavoriteCoinResponse> addFavoriteCoin(@RequestAttribute String subject,
                                                                   @RequestBody String coinInput) {

        AddFavoriteCoinResponse response = favoriteCoinService.addFavoriteCoin(subject, coinInput);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);

    }

    @Operation(summary = "관심 코인 조회", description = "사용자가 추가한 관심 코인 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<GetFavoriteCoinsResponse> getFavoriteCoins(@RequestAttribute String subject) {

        GetFavoriteCoinsResponse response = favoriteCoinService.getFavoriteCoins(subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "관심 코인 선택 삭제", description = "사용자가 추가한 관심 코인을 선택 삭제합니다.")
    @DeleteMapping("/select")
    public ResponseEntity<?> deleteFavoriteCoin(@RequestAttribute String subject,
                                                @RequestBody List<Long> tradingPairIds) {

        DeleteFavoriteCoinResponse response = favoriteCoinService.deleteFavoriteCoin(subject, tradingPairIds);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }

    @Operation(summary = "관심 코인 전체 삭제", description = "사용자가 추가한 관심 코인을 전체 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<?> deleteAllFavoriteCoins(@RequestAttribute String subject) {

        DeleteAllFavoriteCoinResponse response = favoriteCoinService.deleteAllFavoriteCoins(subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);
    }
}

