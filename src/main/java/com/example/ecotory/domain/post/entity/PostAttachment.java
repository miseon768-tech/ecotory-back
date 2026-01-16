package com.example.ecotory.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class PostAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postId;

    private String fileName;
    private Long fileSize;
    private String fileUrl;
    private String fileContentType;


}
