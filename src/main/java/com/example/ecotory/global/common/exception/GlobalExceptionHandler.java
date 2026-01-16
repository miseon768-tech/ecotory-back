package com.example.ecotory.global.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - 리소스 없음(멤버 없음, 자산 없음, 마켓 없음)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

    /*
    memberRepository.findById(subject)
            .orElseThrow(() -> new NoSuchElementException("멤버 없음"));

    tradingPairRepository.findById(addAssetRequest.getTradingPairId())
            .orElseThrow(() -> new NoSuchElementException("트레이딩 페어 없음"));

    postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("커뮤니티 글 없음"));
    */

    // 403 / 409 - 상태/권한 문제(자산 수정 권한 없음, 시뮬레이션 안 함)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", e.getMessage()));
    }

    /*
        if (!post.getMemberId().equals(subject)) {
        throw new IllegalStateException("글 수정 권한 없음"); //403
    }*/

    // 400 - 잘못된 요청 값(수량 음수, 잘못된 파라미터)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    // 500 - 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Internal server error"));
    }
}