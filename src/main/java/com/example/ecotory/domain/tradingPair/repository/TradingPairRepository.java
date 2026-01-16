package com.example.ecotory.domain.tradingPair.repository;

import com.example.ecotory.domain.tradingPair.entity.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {

    // ID로 마켓 조회
    Optional<TradingPair> findById(Long id);

    // 마켓으로 조회
    Optional<TradingPair> findBymarket(String market);

    // 한국어로 조회
    Optional<TradingPair> findByKoreanName(String koreanName);

    // 영어로 조회
    Optional<TradingPair> findByEnglishName(String englishName);


    // 모든 마켓 조회
    List<TradingPair> findAll();

    // 마켓, 한국어 이름, 영어 이름으로 존재 여부 확인
    Optional<TradingPair> findByMarketOrKoreanNameOrEnglishName(String market, String koreanName, String englishName);
}
