package com.example.ecotory.domain.post.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReport {

    @Id
    private String id;

    private String postId;
    private String reporterId;

    private String reason;

    private LocalDateTime reportedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }
}