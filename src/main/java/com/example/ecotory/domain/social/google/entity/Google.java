package com.example.ecotory.domain.social.google.entity;

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

public class Google {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String memberId;

    @Column(name = "provider", nullable = false)
    private String provider; // google

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;
}