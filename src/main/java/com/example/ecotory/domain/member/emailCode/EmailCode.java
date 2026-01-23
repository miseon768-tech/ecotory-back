package com.example.ecotory.domain.member.emailCode;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EmailCode {

    @Id
    private String id;

    private String memberId;

    private String code;

    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        createdAt = LocalDateTime.now();
    }

}
