package com.example.ecotory.domain.member.emailCode;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EmailCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String memberId;

    private String code;

    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
