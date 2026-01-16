package com.example.ecotory.domain.member.repository;

import com.example.ecotory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // 멤버ID로 멤버 조회
    Optional<Member> findById(String subject);

    // 이메일로 멤버 조회
    Optional<Member> findByEmail(String email);

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);

    /*// 소셜 로그인 제공자 + 제공자 ID로 멤버 조회
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);
*/
}
