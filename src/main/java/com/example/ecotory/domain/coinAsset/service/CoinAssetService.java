package com.example.ecotory.domain.coinAsset.service;

import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.CoinBuyAmountUpsertResponse;
import com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary.TotalBuyAmountResponse;
import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import com.example.ecotory.domain.coinAsset.dto.response.CoinAsset.*;
import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
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
    public CoinAssetByTradingPairResponse coinAssetByTradingPair(String subject, Long tradingPairId) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairId(subject, tradingPairId);

        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }

        return CoinAssetByTradingPairResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 마켓으로 자산 검색
    public CoinAssetByMarketResponse coinAssetByMarket(String subject, String market) {

        Member member = memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndMarket(subject, market);

        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByMarketResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 한국어 이름 검색
    public CoinAssetByKoreanResponse coinAssetByKorean(String subject, String koreanName) {
        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairKoreanName(subject, koreanName);
        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByKoreanResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 영어 이름 검색
    public CoinAssetByEnglishResponse coinAssetByEnglish(String subject, String englishName) {
        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> CoinAssetList = coinAssetRepository.findByMemberIdAndTradingPairEnglishName(subject, englishName);
        if (CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return CoinAssetByEnglishResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }

    // 카테고리로 검색
    public CoinAssetByCategoryResponse coinAssetByCategory(String subject, String market, String koreanName, String englishName) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> CoinAssetList = null; // 초기값을 null로 설정

        if(market != null && !market.isEmpty()) {
            CoinAssetList = new ArrayList<>(coinAssetRepository.findByMemberIdAndMarket(subject, market));
        }

        if(koreanName != null && !koreanName.isEmpty()) {
            List<CoinAsset> koreanList = coinAssetRepository.findByMemberIdAndTradingPairKoreanName(subject, koreanName);
            if(CoinAssetList == null) CoinAssetList = new ArrayList<>(koreanList);
            else CoinAssetList.retainAll(koreanList);
        }

        if(englishName != null && !englishName.isEmpty()) {
            List<CoinAsset> englishList = coinAssetRepository.findByMemberIdAndTradingPairEnglishName(subject, englishName);
            if(CoinAssetList == null) CoinAssetList = new ArrayList<>(englishList);
            else CoinAssetList.retainAll(englishList);
        }

        if(CoinAssetList == null || CoinAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }

        return CoinAssetByCategoryResponse.builder()
                .coinAssetList(CoinAssetList)
                .success(true)
                .build();
    }
    // 코인별 매수 금액 입력 및 수정
    public CoinBuyAmountUpsertResponse coinBuyAmountUpsert(String subject, long amount) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        CoinAsset coinAsset = coinAssetRepository.findByMemberId(subject)
                .orElseThrow(() -> new NoSuchElementException("코인 자산 없음"));

        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다");
        } // 어노테이션이 가능했던걸로 기억 ... 찾아봐 ? @Min(1)? positive?


        coinAsset.setBuyAmount(amount);
        coinAssetRepository.save(coinAsset);

        return CoinBuyAmountUpsertResponse.builder()
                .buyAmount(coinAsset.getBuyAmount())
                .success(true)
                .build();
    }

    // 코인별 매수 금액 조회
    public CoinBuyAmountGetResponse coinBuyAmountGet(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        CoinAsset coinAsset = coinAssetRepository.findByMemberId(subject)
                .orElseThrow(() -> new IllegalStateException("값이 없으면 안되는 상태"));


        return CoinBuyAmountGetResponse.builder()
                .success(true)
                .build();
    }

    // 총 매수금액 = 코인별 매수금액의 총합
    public TotalCoinBuyAmountResponse totalCoinBuyAmount(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<CoinAsset> coinAssets = coinAssetRepository.findAllByMemberId(subject);

        long totalBuyAmount = coinAssets.stream()
                .mapToLong(CoinAsset::getBuyAmount)
                .sum();

        return TotalCoinBuyAmountResponse.builder()
                .success(true)
                .totalBuyAmount(totalBuyAmount)
                .build();
    }
}



