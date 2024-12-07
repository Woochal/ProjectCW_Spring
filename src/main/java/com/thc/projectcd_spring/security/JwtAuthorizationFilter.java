package com.thc.projectcd_spring.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thc.projectcd_spring.domain.Tbuser;
import com.thc.projectcd_spring.exceptions.NoMatchingDataException;
import com.thc.projectcd_spring.repository.TbuserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.function.Supplier;

// JWT(JSON Web Token)를 사용하여 사용자의 인증 및 권한 부여를 처리
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final AuthService authService;
    private final ExternalProperties externalProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TbuserRepository tbuserRepository, AuthService authService
            , ExternalProperties externalProperties
    ) {
        super(authenticationManager);
        this.tbuserRepository = tbuserRepository;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }

    // 권한 authorization(인가)를 위한 함수
    // Access Token을 검증하고 유효하면 Authentication을 직접 생성해 SecurityContextHolder에 넣는다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String jwtHeader = request.getHeader(externalProperties.getAccessKey());

        if (jwtHeader == null || !jwtHeader.startsWith(externalProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = jwtHeader.substring(12);

        try {
            // Access Token 검증 시도
            String tbuserId = authService.verifyAccessToken(accessToken);
            authenticateUser(tbuserId, request);

        } catch (JWTVerificationException e) {
            logger.info("AccessToken 만료. 재발급 시도...");

            // Refresh Token 확인
            String refreshToken = request.getHeader("RefreshToken").substring(12);
            logger.info("Refresh Token from header: {}", refreshToken);  // 추가된 로그

            if (refreshToken != null) {
                try {
                    // Refresh Token으로 새로운 Access Token 발급
                    String newAccessToken = authService.issueAccessToken(refreshToken);
                    logger.info("New access token issued: {}", newAccessToken);  // 추가된 로그

                    // 새로운 Access Token을 응답 헤더에 추가
                    response.setHeader(externalProperties.getAccessKey(),
                            externalProperties.getTokenPrefix() + newAccessToken);

                    // 새로운 토큰으로 인증 처리
                    String tbuserId = authService.verifyAccessToken(newAccessToken);
                    authenticateUser(tbuserId, request);

                } catch (Exception refreshError) {
                    logger.error("Refresh token is also invalid", refreshError);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    // 인증 처리를 위한 헬퍼 메서드
    private void authenticateUser(String tbuserId, HttpServletRequest request) {
        Tbuser tbuserEntity = tbuserRepository.findEntityGraphRoleTypeById(tbuserId)
                .orElseThrow(() -> new NoMatchingDataException("id : " + tbuserId));

        request.setAttribute("id", tbuserId);

        PrincipalDetails principalDetails = new PrincipalDetails(tbuserEntity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}