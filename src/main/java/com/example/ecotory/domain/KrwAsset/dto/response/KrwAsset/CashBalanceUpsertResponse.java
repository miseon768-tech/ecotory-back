package com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CashBalanceUpsertResponse {
    private long cashBalance;
    private boolean success;

    public static CashBalanceUpsertResponse fromEntity(long cashBalance, boolean success) {
        return CashBalanceUpsertResponse.builder()
                .cashBalance(cashBalance)
                .success(success)
                .build();
    }
}
