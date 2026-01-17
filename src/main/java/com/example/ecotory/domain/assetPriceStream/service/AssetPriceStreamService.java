package com.example.ecotory.domain.assetPriceStream.service;

import com.example.ecotory.domain.KrwAsset.dto.response.KrwAssetSummary.*;
import com.example.ecotory.domain.KrwAsset.service.KrwAssetService;
import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.coinAsset.service.CoinAssetService;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.global.webSocket.provider.response.TickerResponseByProvider;
import com.example.ecotory.global.webSocket.provider.service.TickerServiceByProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetPriceStreamService {

    private final MemberRepository memberRepository;
    private final CoinAssetRepository coinAssetRepository;
    private final TickerServiceByProvider tickerServiceByProvider;
    private final CoinAssetService coinAssetService;
    private final KrwAssetService krwAssetService;


    // 코인별 평가손익 조회 = 수량 × (현재가 - 평균단가)) - webshcoket 최신가 기준
    public double coinProfit(String subject, String market) {

       

        CoinAsset coinAsset = coinAssetRepository
                .findByMemberIdAndTradingPair_Market(subject, market) // 마켓으로 코인 자산 조회
                .orElseThrow(() -> new NoSuchElementException("해당 코인 자산 없음"));

        TickerResponseByProvider ticker = tickerServiceByProvider.getTicker(market);

        if (ticker == null) {
            throw new RuntimeException("현재가를 가져올 수 없습니다.");
        }

        double balance = coinAsset.getCoinBalance();   // 코인 수량
        double currentPrice = ticker.getTradePrice(); // 최신가(현재가)
        double avgPrice = coinAsset.getAvgBuyPrice();     // 평균 단가(매수평균가)
        double profit = balance * (currentPrice - avgPrice);

        // 평가손익 계산 = 수량 × (현재가 - 평균단가)
        return profit;
    }

    // 총 평가손익 = 코인별 평가손익의 합???
    public double getTotalProfit(String subject) {

       

        List<CoinAsset> coinAssetList = coinAssetRepository.findAllByMemberId(subject);

        if (coinAssetList.isEmpty()) {
            throw new NoSuchElementException("코인 자산 없음");
        }

        double totalProfit = 0.0;
        for (CoinAsset coinAsset : coinAssetList) {

            String market = coinAsset.getTradingPair().getMarket();
            double profit = coinProfit(subject, market);
            totalProfit += profit;
        }

        return totalProfit;

    }

    // 총 평가금액 = 총 매수금액 + 총 평가손익
    public double getTotalEvalAmount(String subject) {

       


        long totalBuyAmount = coinAssetService.totalCoinBuyAmount(subject);
        double totalProfit = getTotalProfit(subject);
        double totalEvalAmount = totalBuyAmount + totalProfit;

        return totalEvalAmount;
    }

    // 총 보유자산 = 총 평가금액(총 매수금액 + 총 평가손익) + 주문 가능 금액
    public double getTotalAssets(String subject) {

        long totalCoinBuyAmount = coinAssetService.totalCoinBuyAmount(subject); // 총 매수금액
        double totalProfit = getTotalProfit(subject); // 총 평가손익
        long cashBalance = krwAssetService.getCashBalance(subject); // 주문 가능 금액

        double totalAsset = totalCoinBuyAmount + totalProfit + cashBalance;

        return totalAsset;
    }

    // 총 수익률 = (총 평가손익 / 총 매수금액) × 100
    public double getTotalProfitRate(String subject) {

        double totalProfit = getTotalProfit(subject);
        long totalBuyAmount = coinAssetService.totalCoinBuyAmount(subject);
        if (totalBuyAmount == 0) {
            return 0.0;
        }

        double totalProfitRate = (totalProfit/totalBuyAmount) * 100;

        return totalProfitRate;
    }
}

// 평가 금액 타임라인 조회
// 자산 거래 히스토리 조회
// 보유자산 포트폴리오(%)
// 자산 대시보드 조회(금액별)