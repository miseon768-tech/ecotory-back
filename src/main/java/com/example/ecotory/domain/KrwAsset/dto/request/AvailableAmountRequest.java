package com.example.ecotory.domain.KrwAsset.dto.request;

import com.example.ecotory.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AvailableAmountRequest {

    private Member member;
    private Double amount;
}
