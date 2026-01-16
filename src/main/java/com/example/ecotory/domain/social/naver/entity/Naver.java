package com.example.ecotory.domain.social.naver.entity;

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

public class Naver {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String memberId;

    @Column(name = "provider", nullable = false)
    private String provider; // naver

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "naver_data", columnDefinition = "TEXT")
    private String naverData; // 필요 시 raw json 저장
}