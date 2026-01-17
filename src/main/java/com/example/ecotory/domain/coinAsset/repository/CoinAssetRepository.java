package com.example.ecotory.domain.coinAsset.repository;

import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoinAssetRepository extends JpaRepository<CoinAsset, String> {
    List<CoinAsset> findByMemberIdAndTradingPairId(String subject, Long tradingPairId);

    List<CoinAsset> findByMemberIdAndMarket(String subject, String market);

    List<CoinAsset> findByMemberIdAndTradingPairKoreanName(String subject, String koreanName);

    List<CoinAsset> findByMemberIdAndTradingPairEnglishName(String subject, String englishName);

    Optional<CoinAsset> findByMemberId(String subject);

    List<CoinAsset> findAllByMemberId(String subject);

    Optional<CoinAsset> findByMemberIdAndTradingPair_Market(String memberId, String market);

}
