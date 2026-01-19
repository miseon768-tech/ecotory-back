package com.example.ecotory.domain.social.repository;

import com.example.ecotory.domain.social.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialRepository extends JpaRepository<Social, String> {
    Optional<Social> findByProviderAndProviderId(String provider, String providerId);
}
