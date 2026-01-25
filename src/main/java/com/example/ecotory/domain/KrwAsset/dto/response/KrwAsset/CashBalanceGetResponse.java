package com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CashBalanceGetResponse {
    private long cashBalance;
    private boolean success;

    public static CashBalanceGetResponse fromEntity(long cashBalance, boolean success) {
        return CashBalanceGetResponse.builder()
                .cashBalance(cashBalance)
                .success(success)
                .build();
    }
}
