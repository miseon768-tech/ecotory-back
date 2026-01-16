package com.example.ecotory.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// @Component 제거! SecurityConfig에서 Bean으로 등록할 예정
public class JWTVerifyFilter extends OncePerRequestFilter {

    // 인증이 필요 없는 요청 필터링
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return uri.startsWith("/api/member")
                || uri.startsWith("/api/tradingPair")
                || uri.startsWith("/api/krwAsset")
                || method.equals("OPTIONS");
    }

    // 인증이 필요한 요청에 대해 JWT 검증 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authorization.substring(7);

        try {
            DecodedJWT jwt = JWT.require(
                    Algorithm.HMAC256("092860be0db7b8fe")
            ).withIssuer("ecotory").build().verify(token);

            String subject = jwt.getSubject();
            request.setAttribute("subject", subject);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}