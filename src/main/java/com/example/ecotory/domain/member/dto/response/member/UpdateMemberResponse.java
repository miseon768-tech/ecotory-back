package com.example.ecotory.domain.member.dto.response.member;

import com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset.CashBalanceUpsertResponse;
import com.example.ecotory.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberResponse {
    Member member;
    private boolean success;

    public static UpdateMemberResponse fromEntity(Member member) {
        return UpdateMemberResponse.builder()
                .member(member)
                .success(true)
                .build();
    }
}
