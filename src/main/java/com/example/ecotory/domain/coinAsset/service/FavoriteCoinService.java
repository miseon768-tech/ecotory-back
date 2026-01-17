package com.example.ecotory.domain.coinAsset.service;

import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.AddFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.DeleteAllFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.DeleteFavoriteCoinResponse;
import com.example.ecotory.domain.coinAsset.dto.response.FavoriteCoin.GetFavoriteCoinsResponse;
import com.example.ecotory.domain.coinAsset.entity.FavoriteCoin;
import com.example.ecotory.domain.coinAsset.repository.FavoriteCoinRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import com.example.ecotory.domain.tradingPair.repository.TradingPairRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "관심 코인 API", description = "관심 코인 추가, 조회, 삭제 API")
@Service
@RequiredArgsConstructor
public class FavoriteCoinService {
    private final FavoriteCoinRepository favoriteCoinRepository;
    private final MemberRepository memberRepository;
    private final TradingPairRepository tradingPairRepository;

    // 관심 코인 등록
    public AddFavoriteCoinResponse addFavoriteCoin(String subject, String coinInput) {

       

        TradingPair tradingPair = tradingPairRepository
                .findByMarketOrKoreanNameOrEnglishName(coinInput, coinInput, coinInput)
                .orElseThrow(() -> new NoSuchElementException("코인 없음"));


        FavoriteCoin favoriteCoin = new FavoriteCoin();
        favoriteCoin.setMemberId(subject);
        favoriteCoin.setTradingPairId(tradingPair.getId());
        favoriteCoinRepository.save(favoriteCoin);


        return AddFavoriteCoinResponse.builder()
                .success(true)
                .build();
    }

    // 관심 코인 목록 전체 조회
    public GetFavoriteCoinsResponse getFavoriteCoins(String subject) {

       

        List<FavoriteCoin> favoriteCoinList = favoriteCoinRepository.findFavoriteCoinByMemberId(subject);

        if (favoriteCoinList.isEmpty()) {
            throw new NoSuchElementException("관심 코인 없음");
        }


        return GetFavoriteCoinsResponse.builder()
                .favoriteCoinList(favoriteCoinList)
                .success(true)
                .build();
    }

    // 관심 코인 선택 삭제
    public DeleteFavoriteCoinResponse deleteFavoriteCoin(String subject, List<Long> tradingPairIds) {

       

        List<FavoriteCoin> favoriteCoinList = favoriteCoinRepository
                .findByMemberIdAndTradingPairIdIn(subject, tradingPairIds);

        if (favoriteCoinList.isEmpty()) {
            throw new NoSuchElementException("관심 코인 없음");
        }

        favoriteCoinRepository.deleteAll(favoriteCoinList);


        return DeleteFavoriteCoinResponse.builder()
                .success(true)
                .build();
    }

    // 관심 코인 전체 삭제
    public DeleteAllFavoriteCoinResponse deleteAllFavoriteCoins(String subject) {

       

        List<FavoriteCoin> favoriteCoinList = favoriteCoinRepository
                .findFavoriteCoinByMemberId(subject);

        if (favoriteCoinList.isEmpty()) {
            throw new NoSuchElementException("관심 코인 없음");
        }

        favoriteCoinRepository.deleteAll(favoriteCoinList);

        return DeleteAllFavoriteCoinResponse.builder()
                .success(true)
                .build();
    }

}
