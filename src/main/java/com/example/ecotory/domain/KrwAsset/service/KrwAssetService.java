package com.example.ecotory.domain.KrwAsset.service;

import com.example.ecotory.domain.KrwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset.*;
import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import com.example.ecotory.domain.KrwAsset.repository.KrwAssetRepository;
import com.example.ecotory.domain.coinAsset.repository.CoinAssetRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import com.example.ecotory.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KrwAssetService {
    private final KrwAssetRepository krwAssetRepository;
    private final MemberRepository memberRepository;
    private final CoinAssetRepository coinAssetRepository;

    // 자산 추가
    public KrwAssetCreateResponse addAsset(Member member, AddAssetRequest addAssetRequest) {

       

        KrwAsset krwAsset = new KrwAsset();
        krwAsset.setCashBalance(addAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(addAssetRequest.getTotalByAmount());

        KrwAsset saved = krwAssetRepository.save(krwAsset);

        return KrwAssetCreateResponse.builder()
                .KrwAsset(saved)
                .success(true)
                .build();
    }

    // 자산 수정
    public AssetUpdateResponse updateAsset(String KrwAssetId, Member member, UpdateAssetRequest updateAssetRequest) {

       

        KrwAsset KrwAsset = krwAssetRepository.findById(KrwAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KrwAsset.getMemberId().equals(member)) {
            throw new IllegalStateException("자산 수정 권한 없음");
        }


        KrwAsset krwAsset = new KrwAsset();
        krwAsset.setCashBalance(updateAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(updateAssetRequest.getTotalByAmount());

        KrwAsset saved = krwAssetRepository.save(krwAsset);

        return AssetUpdateResponse.builder()
                .KrwAsset(saved)
                .success(true)
                .build();
    }

    // 자산 삭제
    public AssetDeleteResponse deleteAsset(String KRWAssetId, Member member) {

       

        KrwAsset KRWAsset = krwAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KRWAsset.getMemberId().equals(member)) {
            throw new IllegalStateException("자산 삭제 권한 없음");
        }

        krwAssetRepository.delete(KRWAsset);

        return AssetDeleteResponse.builder()
                .success(true)
                .build();
    }

    // 특정 멤버 자산 조회
    public KrwAssetByMemberResponse KRWAssetByMember(Member member) {

       


        List<KrwAsset> krwAssetList = krwAssetRepository.findByMemberId(member);


        return KrwAssetByMemberResponse.builder()
                .KrwAssetList(krwAssetList)
                .success(true)
                .build();
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
