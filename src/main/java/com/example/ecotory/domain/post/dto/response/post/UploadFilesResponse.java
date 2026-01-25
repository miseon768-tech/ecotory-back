package com.example.ecotory.domain.post.dto.response.post;

import com.example.ecotory.domain.post.entity.PostAttachment;
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

    public static UploadFilesResponse fromEntity(List<PostAttachment> response) {
        List<String> fileUrls = response.stream()
                .map(PostAttachment::getFileUrl)
                .toList();

        return UploadFilesResponse.builder()
                .fileUrls(fileUrls)
                .success(true)
                .build();
    }
}
