package com.example.ecotory.domain.social.common;


import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.social.google.GoogleService;
import com.example.ecotory.domain.social.naver.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social")
public class SocialController {

    private final GoogleService googleService;
    private final NaverService naverService;

    @GetMapping("/google/callback")
    public ResponseEntity<String> googleCallback(
            @RequestParam String code,
            @RequestParam String state
    ) {
        SocialUserInfo info = googleService.getUserInfo(code, state);
        Member member = googleService.upsertGoogleMember(info);
        return ResponseEntity.ok("Google login success: " + member.getEmail());
    }

    @GetMapping("/naver/callback")
    public ResponseEntity<String> naverCallback(
            @RequestParam String code,
            @RequestParam String state
    ) {
        SocialUserInfo info = naverService.getUserInfo(code, state);
        Member member = naverService.upsertNaverMember(info);
        return ResponseEntity.ok("Naver login success: " + member.getEmail());
    }
}