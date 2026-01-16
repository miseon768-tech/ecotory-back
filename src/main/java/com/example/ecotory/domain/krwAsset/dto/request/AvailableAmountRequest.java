package com.example.ecotory.domain.krwAsset.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AvailableAmountRequest {

    private String subject;
    private Double amount;
}
