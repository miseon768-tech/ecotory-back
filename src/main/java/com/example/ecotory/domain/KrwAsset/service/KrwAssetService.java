package com.example.ecotory.domain.KrwAsset.service;

import com.example.ecotory.domain.KrwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset.*;
import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import com.example.ecotory.domain.KrwAsset.repository.KrwAssetRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KrwAssetService {

    private final KrwAssetRepository krwAssetRepository;

    // 자산 추가
    public KrwAsset addAsset(AddAssetRequest request) {

        KrwAsset krwAsset = request.toEntity();

        krwAssetRepository.save(krwAsset);

        return krwAsset;
    }

    // 자산 수정
    public KrwAsset updateAsset(String KrwAssetId, Member member, UpdateAssetRequest request) {

        KrwAsset krwAsset = krwAssetRepository.findById(KrwAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!krwAsset.getMemberId().equals(member.getId())) {
            throw new IllegalStateException("자산 수정 권한 없음");
        }

        krwAsset.setCashBalance(request.getCashBalance());
        krwAsset.setTotalByAmount(request.getTotalByAmount());


        return krwAssetRepository.save(krwAsset);
    }

    // 자산 삭제
    public KrwAsset deleteAsset(String KRWAssetId, Member member) {

        KrwAsset krwAsset = krwAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!krwAsset.getMemberId().equals(member.getId())) {
            throw new IllegalStateException("자산 삭제 권한 없음");
        }

        krwAssetRepository.delete(krwAsset);

        return krwAsset;
    }

    // 특정 멤버 자산 조회
    public List<KrwAsset> KrwAssetByMember(Member member) {

        return krwAssetRepository.findByMemberId(member);
    }

    // 주문 가능 금액(=보유 KRW) 입력 및 수정 : 사용자가 직접 입력, 추가 금액 기재시 더함? 더할 필요가 있나 그냥 수정을 하면 될듯
    public long upsertCashBalance(Member member, long amount) {

        KrwAsset krwAsset = krwAssetRepository.findByMember(member)
                .orElseThrow(() -> new NoSuchElementException("KRW 자산 없음"));

        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다");
        } // 어노테이션이 가능했던걸로 기억 ... 찾아봐 ? @Min(1)? positive?


        krwAsset.setCashBalance(amount);
        krwAssetRepository.save(krwAsset);


        return krwAsset.getCashBalance();
    }

    // 주문 가능 금액 조회
    public long getCashBalance(Member member){

        KrwAsset krwAsset = krwAssetRepository.findByMember(member)
                .orElseThrow(() -> new IllegalStateException("값이 없으면 안되는 상태"));

        return krwAsset.getCashBalance();
    }

}
