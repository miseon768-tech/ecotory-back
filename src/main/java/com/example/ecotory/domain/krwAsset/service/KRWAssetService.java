package com.example.ecotory.domain.krwAsset.service;

import com.example.ecotory.domain.krwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.response.KRWassets.*;
import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import com.example.ecotory.domain.krwAsset.repository.KrwAssetRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KRWAssetService {
    private final KrwAssetRepository KRWAssetRepository;
    private final MemberRepository memberRepository;

    // 자산 추가
    public AddKRWAssetResponse addAsset(String subject, AddAssetRequest addAssetRequest) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        KRWAsset krwAsset = new KRWAsset();
        krwAsset.setCashBalance(addAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(addAssetRequest.getTotalByAmount());

        KRWAsset saved = KRWAssetRepository.save(krwAsset);

        return AddKRWAssetResponse.builder()
                .KRWAsset(saved)
                .success(true)
                .build();
    }

    // 자산 수정
    public UpdateAssetResponse updateAsset(String KRWAssetId, String subject, UpdateAssetRequest updateAssetRequest) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        KRWAsset KRWAsset = KRWAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KRWAsset.getMemberId().equals(subject)) {
            throw new IllegalStateException("자산 수정 권한 없음");
        }


        KRWAsset krwAsset = new KRWAsset();
        krwAsset.setCashBalance(updateAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(updateAssetRequest.getTotalByAmount());

        KRWAsset saved = KRWAssetRepository.save(krwAsset);

        return UpdateAssetResponse.builder()
                .KRWAsset(saved)
                .success(true)
                .build();
    }

    // 자산 삭제
    public DeleteAssetResponse deleteAsset(String KRWAssetId, String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        KRWAsset KRWAsset = KRWAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KRWAsset.getMemberId().equals(subject)) {
            throw new IllegalStateException("자산 삭제 권한 없음");
        }

        KRWAssetRepository.delete(KRWAsset);

        return DeleteAssetResponse.builder()
                .success(true)
                .build();
    }

    // 특정 멤버 자산 조회
    public KRWAssetByMemberResponse KRWAssetByMember(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));


        List<KRWAsset> KRWAssetList = KRWAssetRepository.findByMemberId(subject);


        return KRWAssetByMemberResponse.builder()
                .KRWAssetList(KRWAssetList)
                .success(true)
                .build();
    }
}
