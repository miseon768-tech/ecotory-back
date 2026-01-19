package com.example.ecotory.domain.asset.repository;

import com.example.ecotory.domain.asset.entity.Asset;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findAllByMember(Member member);
}
