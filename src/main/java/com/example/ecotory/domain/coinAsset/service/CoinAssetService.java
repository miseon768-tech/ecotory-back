package com.example.ecotory.domain.coinAsset.service;

import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.*;
import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CoinAssetService {
    private final MemberRepository memberRepository;
    private final CoinAssetRepository coinAssetRepository;


    // 트레이딩 페어로 자산 검색
    public CoinAssetByTradingPairResponse coinAssetByTradingPair(Member member, Long tradingPairId) {

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairId(member, tradingPairId);

        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }

        return CoinAssetByTradingPairResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 마켓으로 자산 검색
    public CoinAssetByMarketResponse coinAssetByMarket(Member member, String market) {

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndMarket(member, market);

        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByMarketResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 한국어 이름 검색
    public CoinAssetByKoreanResponse coinAssetByKorean(Member member, String koreanName) {
       

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairKoreanName(member, koreanName);
        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByKoreanResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 영어 이름 검색
    public CoinAssetByEnglishResponse coinAssetByEnglish(Member member, String englishName) {
       

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairEnglishName(member, englishName);
        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByEnglishResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 카테고리로 검색
    public CoinAssetByCategoryResponse coinAssetByCategory(Member member, String market, String koreanName, String englishName) {

       

        List<CoinAsset> CoinAssetList = null; // 초기값을 null로 설정

        if (market != null && !market.isEmpty()) {
            CoinAssetList = new ArrayList<>(coinAssetRepository.findByMemberIdAndMarket(member, market));
        }

        if (koreanName != null && !koreanName.isEmpty()) {
            List<CoinAsset> koreanList = coinAssetRepository.findByMemberIdAndTradingPairKoreanName(member, koreanName);
            if (CoinAssetList == null) CoinAssetList = new ArrayList<>(koreanList);
            else CoinAssetList.retainAll(koreanList);
        }

        if (englishName != null && !englishName.isEmpty()) {
            List<CoinAsset> englishList = coinAssetRepository.findByMemberIdAndTradingPairEnglishName(member, englishName);
            if (CoinAssetList == null) CoinAssetList = new ArrayList<>(englishList);
            else CoinAssetList.retainAll(englishList);
        }

        if (CoinAssetList == null || CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }

        return CoinAssetByCategoryResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 코인별 매수 금액 입력 및 수정
    public long coinBuyAmountUpsert(Member member, long amount) {

        CoinAsset coinAsset = coinAssetRepository.findByMemberId(member)
                .orElseThrow(() -> new NoSuchElementException("코인 자산 없음"));

        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다");
        } // 어노테이션이 가능했던걸로 기억 ... 찾아봐 ? @Min(1)? positive?


        coinAsset.setBuyAmount(amount);
        coinAssetRepository.save(coinAsset);

        return coinAsset.getBuyAmount();
    }

    // 코인별 매수 금액 조회
    public long coinBuyAmountGet(Member member) {

        CoinAsset coinAsset = coinAssetRepository.findByMemberId(member)
                .orElseThrow(() -> new IllegalStateException("값이 없으면 안되는 상태"));


        return coinAsset.getBuyAmount();
    }

    // 총 매수금액 = 코인별 매수금액의 총합
    public long totalCoinBuyAmount(Member member) {

        List<CoinAsset> coinAssetList = coinAssetRepository.findAllByMemberId(member);

        long result = coinAssetList.stream()
                .mapToLong(CoinAsset::getBuyAmount)
                .sum();

        return result;

    }
}
