package com.example.ecotory.domain.asset.entity;

import com.example.ecotory.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long memberId;
    private Long marketId;

    private String symbol;
    private String name;
    private String type;
    private String exchange;

    private Double amount;
    private Double avgPrice;

    LocalDateTime created_at;
    LocalDateTime updated_at;


    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now();
        updated_at = created_at.plusMinutes(10);
    }
}
