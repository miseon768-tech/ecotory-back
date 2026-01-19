package com.example.ecotory.domain.social.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social, String> {
    Optional<Social> findByProviderAndProviderId(String provider, String providerId);
    Optional<Social> findByProviderAndEmail(String provider, String email);
}