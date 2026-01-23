package com.example.ecotory.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
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
public class VerifyFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getRequestURI().startsWith("/api/chat")){
            return false;
        }
        return true;
    }

    // JWT 인증토큰 검증 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 인증토큰 값을 보낼때 보통 헤더는 Authorization 으로 설정하는 게 국룰
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);

        // 인증토큰 종류가 여러가지가 있다.
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);    // 401
            return;
        }

        String token = authorization.substring(7);

        // JWTVerifier 생성
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("092860be0db7b8fe")).withIssuer("quickchat").build();

        // JWT 검증
        try {
            DecodedJWT jwt = verifier.verify(token);
            Long subject = Long.valueOf(jwt.getSubject());
            request.setAttribute("subject", subject);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);    // 401
        }

    }


}
