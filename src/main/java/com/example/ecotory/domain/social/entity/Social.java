package com.example.ecotory.domain.social.entity;

import com.example.ecotory.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Social {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String provider;

    private String providerId;

}