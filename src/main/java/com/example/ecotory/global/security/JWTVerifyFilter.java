package com.example.ecotory.global.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ecotory.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTVerifyFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    public JWTVerifyFilter(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return uri.startsWith("/api/member")
                || uri.startsWith("/api/tradingPair")
                || uri.startsWith("/api/krwAsset")
                || method.equals("OPTIONS");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String token = authorization.substring(7);

            DecodedJWT jwt = JWT.require(
                            Algorithm.HMAC256("092860be0db7b8fe")
                    ).withIssuer("ecotory")
                    .build()
                    .verify(token);

            String subject = jwt.getSubject();

            // 여기서 로그인 사용자 보장 (실무 핵심)
            memberRepository.findById(subject)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 컨트롤러 / 서비스에서 사용
            request.setAttribute("subject", subject);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}