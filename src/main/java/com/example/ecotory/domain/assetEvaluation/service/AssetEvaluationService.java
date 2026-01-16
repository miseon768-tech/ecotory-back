package com.example.ecotory.domain.assetEvaluation.service;

import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary.*;
import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import com.example.ecotory.domain.krwAsset.repository.KrwAssetRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.global.webSocket.price.MarketPriceCache;
import com.example.ecotory.global.webSocket.provider.response.OrderbookResponseByProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class  AssetEvaluationService {

    private final MemberRepository memberRepository;
    private final KrwAssetRepository krwAssetRepository;
    private final CoinAssetRepository coinAssetRepository;
    private final MarketPriceCache marketPriceCache;


    // 총 평가손익 = 코인별 평가손익의 합??? - webshcoket 최신가 기준 (코인별 평가손익 = 수량 × (현재가 - 평균단가))
    public TotalProfitResponse getTotalProfit(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));


        List<krwAsset> krwAssets = KrwAssetRepository.findByMemberId(subject);
        Map<String, OrderbookResponseByProvider> orderbooks = orderbookService.getAllOrderbooks();

        double totalProfit = krwAssets.stream()
                .mapToDouble(a -> {
                    OrderbookResponseByProvider ob = getLatestOrderbook(a.getTradingPair(), orderbooks);
                    double price = (ob != null && !ob.getOrderbookUnits().isEmpty())
                            ? ob.getOrderbookUnits().get(0).getAskPrice()
                            : a.getAvgPrice();
                    return a.getAmount() * (price - a.getAvgPrice());
                })
                .sum();

        return TotalProfitResponse.builder()
                .success(true)
                .totalProfit(totalProfit)
                .build();
    }

    // 총 평가금액 = 총 매수금액(krwAsset.totalByAmount) + 총 평가손익(coinAsset.totalProfit)
    public TotalEvalAmountResponse getTotalEvalAmount(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        // 멤버 존재 여부 확인
        List<krwAsset> krwAssets = KrwAssetRepository.findByMemberId(subject);

        // 최신 오더북 조회
        Map<String, OrderbookResponseByProvider> orderbooks = orderbookServiceByProvider.getAllOrderbooks();


        // 총 평가금액 계산
        double totalEvalAmount = krwAssets.stream()
                .mapToDouble(a -> {
                    OrderbookResponseByProvider ob = getLatestOrderbook(a.getTradingPair(), orderbooks);
                    double price = (ob != null && !ob.getOrderbookUnits().isEmpty()) // 최신가 우선
                            ? ob.getOrderbookUnits().get(0).getAskPrice() // 현재가
                            : a.getAvgPrice(); // 없으면 평균단가 사용
                    return a.getAmount() * price; // 평가금액 = 수량 × 현재가
                })
                .sum();

        // 응답 반환
        return TotalEvalAmountResponse.builder()
                .success(true)
                .totalEvalAmount(totalEvalAmount)
                .build();
    }

    // 총 보유자산 = 총 평가금액 + 주문 가능 금액(krwAsset.cashBalance)
    public TotalAssetsResponse getTotalAssets(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        // 총 평가금액
        double totalEvalAmount = getTotalEvalAmount(subject).getTotalEvalAmount();

        // 주문 가능 금액 (DB에서 직접 가져올 수 있음)
        double availableAmount = getAvailableAmount(subject).getAvailableAmount();

        return TotalAssetsResponse.builder()
                .success(true)
                .totalAssets(totalEvalAmount + availableAmount)
                .build();
    }

    // 총 수익률 = (총 평가손익 / 총 매수금액(krwAsset.totalByAmount)) × 100
    public TotalProfitRateResponse getTotalProfitRate(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));


        double totalProfit = getTotalProfit(subject).getTotalProfit();
        double totalBuyAmount = TotalBuyAmount(subject).getTotalBuyAmount();

        double profitRate = totalBuyAmount != 0 ? (totalProfit / totalBuyAmount) * 100 : 0;

        return TotalProfitRateResponse.builder()
                .success(true)
                .totalProfitRate(profitRate)
                .build();
    }

    /*// 평가 금액 타임라인 (예시: DB에서 시간별 평가 금액)
    public EvalAmountTimelineResponse getEvalAmountTimeline(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));


        // TODO: 실제 타임라인은 DB 또는 캐시 기록 활용 필요
        return EvalAmountTimelineResponse.builder()
                .success(true)
                .build();
    }

    // 최신 Orderbook 가져오기
    private OrderbookResponseByProvider getLatestOrderbook(String tradingPair, Map<String, OrderbookResponseByProvider> orderbooks) {


        return orderbooks.entrySet().stream()
                .filter(e -> e.getKey().startsWith(tradingPair + "-"))
                .max((e1, e2) -> Long.compare(
                        Long.parseLong(e1.getKey().split("-")[1]),
                        Long.parseLong(e2.getKey().split("-")[1])
                ))
                .map(Map.Entry::getValue)
                .orElse(null);
    }*/
}