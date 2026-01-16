package com.example.ecotory.domain.social.kakao.entity;

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

public class Kakao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    private String memberId;

    @Column(name = "provider", nullable = false)
    private String provider; // kakao

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "kakao_data", columnDefinition = "TEXT")
    private String kakaoData; // 필요 시 raw json 저장
}