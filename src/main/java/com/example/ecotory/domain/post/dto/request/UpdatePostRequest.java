package com.example.ecotory.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostRequest {

    private String title;
    private String content;

}