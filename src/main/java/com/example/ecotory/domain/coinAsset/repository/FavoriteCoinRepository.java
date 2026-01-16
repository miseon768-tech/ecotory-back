package com.example.ecotory.domain.coinAsset.repository;

import com.example.ecotory.domain.coinAsset.entity.FavoriteCoin;
import com.example.ecotory.domain.coinAsset.entity.FavoriteCoinId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteCoinRepository extends JpaRepository<FavoriteCoin, FavoriteCoinId> {

    // 멤버 ID로 즐겨찾기 코인 조회
    List<FavoriteCoin> findFavoriteCoinByMemberId(String memberId);

    // 멤버 ID와 트레이딩 페어 ID로 즐겨찾기 코인 조회
    List<FavoriteCoin> findByMemberIdAndTradingPairIdIn(String subject, List<Long> tradingPairIds);
}

