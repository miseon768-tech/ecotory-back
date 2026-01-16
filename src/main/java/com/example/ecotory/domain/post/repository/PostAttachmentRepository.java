package com.example.ecotory.domain.post.repository;

import com.example.ecotory.domain.post.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Long> {



}
