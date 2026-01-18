package com.example.ecotory.domain.assetPriceStream.service;

import com.example.ecotory.domain.KrwAsset.service.KrwAssetService;
import com.example.ecotory.domain.assetPriceStream.model.PortfolioItem;
import com.example.ecotory.domain.assetPriceStream.response.AssetDashboardResponse;
import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.coinAsset.service.CoinAssetService;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.global.webSocket.provider.response.TickerResponseByProvider;
import com.example.ecotory.global.webSocket.provider.service.TickerServiceByProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        // 평가손익 계산 = 수량 × (현재가 - 평균단가)
        return balance * (currentPrice - avgPrice);
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

    // 코인별 평가금액 = 코인별 매수금액 + 코인별 평가손익
    public double coinEvalAmount(String subject, String market) {

        coinAssetRepository.findByMemberIdAndTradingPair_Market(subject, market) // 마켓으로 코인 자산 조회
                .orElseThrow(() -> new NoSuchElementException("해당 코인 자산 없음"));

        long coinBuyAmount = coinAssetService.coinBuyAmountGet(subject); // 코인별 매수금액
        double profit = coinProfit(subject, market);    // 코인별 평가손익

        return coinBuyAmount + profit;
    }

    // 총 평가금액 = 총 매수금액 + 총 평가손익 = 모든 코인별 평가금액 합
    public double getTotalEvalAmount(String subject) {

        List<CoinAsset> coinAssetList = coinAssetRepository.findAllByMemberId(subject);

        if (coinAssetList.isEmpty()) {
            return 0.0;
        }

        double totalEvalAmount = 0.0;

        for (CoinAsset coinAsset : coinAssetList) {
            String market = coinAsset.getTradingPair().getMarket();
            totalEvalAmount += coinEvalAmount(subject, market);
        }

        return totalEvalAmount;
    }

    // 총 보유자산 = 총 평가금액(총 매수금액 + 총 평가손익) + 주문 가능 금액
    public double getTotalAssets(String subject) {

        double getTotalEvalAmount = getTotalEvalAmount(subject); // 총 평가금액
        long cashBalance = krwAssetService.getCashBalance(subject); // 주문 가능 금액

        return getTotalEvalAmount + cashBalance;
    }

    // 총 수익률 = (총 평가손익 / 총 매수금액) × 100
    public double getTotalProfitRate(String subject) {

        double totalProfit = getTotalProfit(subject);
        long totalBuyAmount = coinAssetService.totalCoinBuyAmount(subject);
        if (totalBuyAmount == 0) {
            return 0.0;
        }

        return (totalProfit / totalBuyAmount) * 100;
    }

    // 보유자산 포트폴리오(%)
    public List<PortfolioItem> portfolioAsset(String subject) {

        List<CoinAsset> coinAssetList = coinAssetRepository.findAllByMemberId(subject);
        double totalAssets = getTotalAssets(subject); // 총 보유자산 = 총 평가금액 + 주문 가능 금액

        List<PortfolioItem> portfolio = new ArrayList<>();

        // 코인별 평가금액 비율 계산 = (코인별 평가금액 / 총 보유 자산) * 100
        for (CoinAsset coinAsset : coinAssetList) {
            String market = coinAsset.getTradingPair().getMarket(); // 마켓 정보
            double valuation = coinEvalAmount(subject, market); // 코인별 평가금액

            portfolio.add(new PortfolioItem(
                    market.split("-")[0], // 자산명 (코인 심볼)
                    valuation, // 평가금액
                    (totalAssets == 0) ? 0.0 : (valuation / totalAssets) * 100)); // 비율
        }

        long cashBalance = krwAssetService.getCashBalance(subject);

        // KRW 비율 계산 = (주문 가능 금액 / 총 보유 자산) * 100
        portfolio.add(new PortfolioItem(
                "KRW", // 자산명
                cashBalance, // 평가금액 = 주문 가능 금액(손익계산이 없기 때문)
                (totalAssets == 0) ? 0.0 : (cashBalance / totalAssets) * 100)); // 비율

        return portfolio;
    }
}


