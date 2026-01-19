package com.example.ecotory.domain.market.provider.response.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Error {

    Integer name;
    String message;
}
