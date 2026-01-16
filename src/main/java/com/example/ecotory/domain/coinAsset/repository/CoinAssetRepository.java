package com.example.ecotory.domain.coinAsset.repository;

import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinAssetRepository extends JpaRepository<CoinAsset, String> {
    List<CoinAsset> findByMemberIdAndTradingPairId(String subject, Long tradingPairId);

    List<CoinAsset> findByMemberIdAndMarket(String subject, String market);

    List<CoinAsset> findByMemberIdAndTradingPairKoreanName(String subject, String koreanName);

    List<CoinAsset> findByMemberIdAndTradingPairEnglishName(String subject, String englishName);
}
