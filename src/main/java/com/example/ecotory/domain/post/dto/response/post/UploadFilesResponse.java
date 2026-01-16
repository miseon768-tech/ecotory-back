package com.example.ecotory.domain.post.dto.response.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UploadFilesResponse {
    private List<String> fileUrls;
    private boolean success;
}
