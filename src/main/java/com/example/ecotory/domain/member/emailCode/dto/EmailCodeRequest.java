package com.example.ecotory.domain.member.emailCode.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EmailCodeRequest {

    private String email;
    private String code;
}
