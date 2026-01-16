package com.example.ecotory.domain.post.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post Report", description = "글 신고 API")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/report")
public class PostReportController {


    //    글 신고
//    신고된 글 목록

}
