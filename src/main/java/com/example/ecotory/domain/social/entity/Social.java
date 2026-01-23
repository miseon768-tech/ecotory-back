package com.example.ecotory.domain.social.entity;

import com.example.ecotory.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Social {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String provider;

    private String providerId;


    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }
}