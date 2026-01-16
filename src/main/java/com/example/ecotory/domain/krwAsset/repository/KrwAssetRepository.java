package com.example.ecotory.domain.krwAsset.repository;

import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KrwAssetRepository extends JpaRepository<KRWAsset, String> {

    // JWT subject 기준으로 전체 자산 조회
    List<KRWAsset> findByMemberId(String memberId);

    Optional<KRWAsset> findByMember(String subject);
}


