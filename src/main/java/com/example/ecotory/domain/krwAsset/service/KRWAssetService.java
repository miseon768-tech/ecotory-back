package com.example.ecotory.domain.krwAsset.service;

import com.example.ecotory.domain.krwAsset.dto.request.AddAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.request.UpdateAssetRequest;
import com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary.CashBalanceResponse;
import com.example.ecotory.domain.krwAsset.dto.response.KRWAssetsSummary.TotalBuyAmountResponse;
import com.example.ecotory.domain.krwAsset.dto.response.KRWassets.*;
import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import com.example.ecotory.domain.krwAsset.repository.KrwAssetRepository;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KRWAssetService {
    private final KrwAssetRepository krwAssetRepository;
    private final MemberRepository memberRepository;

    // 자산 추가
    public AddKRWAssetResponse addAsset(String subject, AddAssetRequest addAssetRequest) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        krwAsset krwAsset = new krwAsset();
        krwAsset.setCashBalance(addAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(addAssetRequest.getTotalByAmount());

        krwAsset saved = krwAssetRepository.save(krwAsset);

        return AddKRWAssetResponse.builder()
                .KRWAsset(saved)
                .success(true)
                .build();
    }

    // 자산 수정
    public UpdateAssetResponse updateAsset(String KRWAssetId, String subject, UpdateAssetRequest updateAssetRequest) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        krwAsset KRWAsset = krwAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KRWAsset.getMemberId().equals(subject)) {
            throw new IllegalStateException("자산 수정 권한 없음");
        }


        krwAsset krwAsset = new krwAsset();
        krwAsset.setCashBalance(updateAssetRequest.getCashBalance());
        krwAsset.setTotalByAmount(updateAssetRequest.getTotalByAmount());

        krwAsset saved = krwAssetRepository.save(krwAsset);

        return UpdateAssetResponse.builder()
                .KRWAsset(saved)
                .success(true)
                .build();
    }

    // 자산 삭제
    public DeleteAssetResponse deleteAsset(String KRWAssetId, String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        krwAsset KRWAsset = krwAssetRepository.findById(KRWAssetId)
                .orElseThrow(() -> new NoSuchElementException("자산 없음"));

        if (!KRWAsset.getMemberId().equals(subject)) {
            throw new IllegalStateException("자산 삭제 권한 없음");
        }

        krwAssetRepository.delete(KRWAsset);

        return DeleteAssetResponse.builder()
                .success(true)
                .build();
    }

    // 특정 멤버 자산 조회
    public KRWAssetByMemberResponse KRWAssetByMember(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));


        List<krwAsset> krwAssetList = krwAssetRepository.findByMemberId(subject);


        return KRWAssetByMemberResponse.builder()
                .krwAssetList(krwAssetList)
                .success(true)
                .build();
    }

    // 주문 가능 금액(=보유 KRW 잔고, krwAsset.cashBalance) : 사용자가 직접 입력, 추가 금액 기재시 더함? 더할 필요가 있나 그냥 수정을 하면 될듯
    public CashBalanceResponse cashBalance(String subject, double amount) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        krwAsset krwAsset = krwAssetRepository.findByMember(subject)
                .orElseThrow(() -> new NoSuchElementException("KRW 자산 없음"));

        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다");
        } // 어노테이션이 가능했던걸로 기억 ... 찾아봐 ? @Min(1)? positive?


        krwAsset.setCashBalance(amount);
        krwAssetRepository.save(krwAsset);


        return CashBalanceResponse.builder()
                .cashBalance(krwAsset.getCashBalance())
                .success(true)
                .build();
    }

    // 코인별 매수 금액 조회
    public KRWAssetByKoreanResponse KRWAssetByKorean(String subject, String koreanName) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<krwAsset> krwAssetList = krwAssetRepository.findByMemberIdAndKoreanName(subject, koreanName);
        if (krwAssetList.isEmpty()) {
            throw new NoSuchElementException("자산 없음");
        }
        return KRWAssetByKoreanResponse.builder()
                .krwAssetList(krwAssetList)
                .success(true)
                .build();
    }

    // 총 매수금액 = 코인별 매수금액의 총합
    public TotalBuyAmountResponse TotalBuyAmount(String subject) {

        memberRepository.findById(subject)
                .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

        List<krwAsset> krwAssetList = krwAssetRepository.findByMemberId(subject);

        double totalBuyAmount = krwAssetList.stream()
                .mapToDouble(a -> a.getAmount() * a.getAvgPrice())
                .sum();

        return TotalBuyAmountResponse.builder()
                .success(true)
                .totalBuyAmount(totalBuyAmount)
                .build();
    }

}
