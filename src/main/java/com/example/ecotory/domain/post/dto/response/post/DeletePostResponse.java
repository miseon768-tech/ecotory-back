package com.example.ecotory.domain.post.dto.response.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeletePostResponse {
    private boolean success;
}
