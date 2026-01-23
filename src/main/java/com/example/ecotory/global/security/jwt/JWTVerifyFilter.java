package com.example.ecotory.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTVerifyFilter extends OncePerRequestFilter {

    // 특정 요청에 대해서는 필터를 적용하지 않도록 설정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 요청 URI와 메서드 확인
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // "/api/member"로 시작하는 URI와 OPTIONS 메서드에 대해서는 필터를 적용하지 않음
        if (uri.startsWith("/api/member") || method.equals("OPTIONS")) {
            return true;
        } else {
            return false;
        }
    }

    // 실제 필터링 로직
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 토큰 추출
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.getWriter().println("{\"success\":false}");
            return;
        }

        // 토큰 검증
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256("master")).withIssuer("ecotory").build().verify(token);
        } catch (Exception e) {
            response.getWriter().println("{\"success\":false}");
            return;
        }

        // 토큰에서 사용자 정보 추출하여 요청 속성에 설정
        int subject = Integer.parseInt(jwt.getSubject());
        request.setAttribute("currentAccountId", subject);

        filterChain.doFilter(request, response);
    }
}
