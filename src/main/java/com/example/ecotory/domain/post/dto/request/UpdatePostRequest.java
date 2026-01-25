package com.example.ecotory.domain.post.dto.request;

import com.example.ecotory.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostRequest {

    private String title;
    private String content;

    public Post updatePost(){
        return Post.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .build();
    }

}