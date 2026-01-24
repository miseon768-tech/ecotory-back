package com.example.ecotory.domain.coinAsset.repository;

import com.example.ecotory.domain.coinAsset.entity.CoinAsset;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoinAssetRepository extends JpaRepository<CoinAsset, String> {
    List<CoinAsset> findByMemberIdAndTradingPairId(Member member, Long tradingPairId);

    List<CoinAsset> findByMemberIdAndMarket(Member member, String market);

    List<CoinAsset> findByMemberIdAndTradingPairKoreanName(Member member, String koreanName);

    List<CoinAsset> findByMemberIdAndTradingPairEnglishName(Member member, String englishName);

    Optional<CoinAsset> findByMemberId(Member member);

    List<CoinAsset> findAllByMemberId(Member member);

    Optional<CoinAsset> findByMemberIdAndTradingPair_Market(Member member, String market);

}
