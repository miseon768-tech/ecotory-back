package com.example.ecotory.domain.KrwAsset.repository;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KrwAssetRepository extends JpaRepository<KrwAsset, String> {

   

    Optional<KrwAsset> findByMember(Member member);

    List<KrwAsset> findByMemberId(Member member);
}
