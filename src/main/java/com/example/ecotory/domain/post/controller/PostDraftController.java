package com.example.ecotory.domain.post.controller;

import com.example.ecotory.domain.post.dto.response.postDraft.GetDraftResponse;
import com.example.ecotory.domain.post.dto.response.postDraft.SaveDraftResponse;
import com.example.ecotory.domain.post.service.PostDraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post Draft", description = "글 임시 저장 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/draft")
public class PostDraftController {

    private final PostDraftService postDraftService;

    @Operation(summary = "글 임시 저장", description = "작성 중인 글을 임시로 저장합니다.")
    @PostMapping
    public ResponseEntity<SaveDraftResponse> saveDraft(@RequestAttribute String subject,
                                       @RequestParam String title,
                                       @RequestParam String content) {

        SaveDraftResponse response = postDraftService.saveDraft(subject, title, content);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);

    }

    @Operation(summary = "임시글 불러오기", description = "작성자가 임시로 저장한 글 목록을 불러옵니다.")
    @GetMapping
    public ResponseEntity<GetDraftResponse> getDrafts(@RequestAttribute String subject) {

        GetDraftResponse response = postDraftService.getDraft(subject);

        return ResponseEntity.status(HttpStatus.OK) //200
                .body(response);


    }
}
