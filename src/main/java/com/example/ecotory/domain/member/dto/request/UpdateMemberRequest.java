package com.example.ecotory.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberRequest {

    String email;
    String nickname;
    
    public UpdateMemberRequest toEntity() {
        return UpdateMemberRequest.builder()
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
            
            
}
