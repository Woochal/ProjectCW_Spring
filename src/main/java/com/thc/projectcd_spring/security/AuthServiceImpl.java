package com.thc.projectcd_spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thc.projectcd_spring.domain.RefreshToken;
import com.thc.projectcd_spring.dto.RefreshtokenDto;
import com.thc.projectcd_spring.exceptions.InvalidTokenException;
import com.thc.projectcd_spring.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service // Spring의 서비스 컴포넌트로 등록
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExternalProperties externalProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    public AuthServiceImpl(
            ExternalProperties externalProperties
            , RefreshTokenRepository refreshTokenRepository
    ) {
        this.externalProperties = externalProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    // JWT 토큰 생성에 사용할 알고리즘 반환
    public Algorithm getTokenAlgorithm() {

        return Algorithm.HMAC512(externalProperties.getTokenSecretKey());
    }


    @Override
    // Access Token 생성을 위한 메서드. Payload에 유저 Id를 담는다.
    public String createAccessToken(String tbuserId) {
        return JWT.create() // JWT 객체 생성
                .withSubject("accessToken") // 주제 설정
                .withClaim("id", tbuserId) // 사용자 ID를 클레임에 추가
                .withExpiresAt(new Date(System.currentTimeMillis() + externalProperties.getAccessTokenExpirationTime())) // 만료 시간 설정
                .sign(getTokenAlgorithm()); // 서명하여 토큰 생성
    }


    @Override
    // Access Token 검증을 위한 메서드
    public String verifyAccessToken(String accessToken) throws JWTVerificationException {
        return JWT.require(getTokenAlgorithm()) // 알고리즘을 사용하여 JWT 요구
                .build() // JWT 빌더 생성
                .verify(accessToken) // 토큰 검증
                .getClaim("id").asString(); // 클레임에서 사용자 ID 반환
    }


    @Override
    // Refresh Token 생성을 위한 함수. Payload에 tbuser Id를 담는다. DB에 저장할수도 있음.
    // redis에 tbuserId를 key로, 발급한 Refresh Token을 value로 저장할 수도있음.
    public String createRefreshToken(String tbuserId) {

        System.out.println("revokeRefreshToken");
        // refreshToken 기존꺼 지우기 (로그인 하면 이전 리프레시 토큰 지우는 기능)
        revokeRefreshToken(tbuserId);

        System.out.println("revokeRefreshToken2");

        String refreshToken = JWT.create()
                .withSubject("refreshToken")
                .withClaim("id", tbuserId)
                .withExpiresAt(new Date(System.currentTimeMillis()+externalProperties.getRefreshTokenExpirationTime()))
                .sign(getTokenAlgorithm());

        // 디비에 refreshToken 저장
        refreshTokenRepository.save(new RefreshtokenDto.CreateReqDto(tbuserId, refreshToken).toEntity());

        return refreshToken;
    }


    @Override
    // refreshToken 삭제를 위한 메서드. 유저 Id로 조회해서 모두 지운다.
    public void revokeRefreshToken(String tbuserId) {
        refreshTokenRepository.deleteAll(refreshTokenRepository.findByTbuserId(tbuserId));
    }



    @Override
    // Refresh Token 검증을 위한 함수
    public String verifyRefreshToken(String refreshToken) throws JWTVerificationException {
        logger.info("Verifying refresh token: {}", refreshToken);

        try {
            // DB에서 리프레시 토큰 조회
            RefreshToken storedToken = refreshTokenRepository.findByContent(refreshToken)
                    .orElseThrow(() -> {
                        logger.error("Refresh token not found in database");
                        return new InvalidTokenException("Refresh token not found");
                    });

            logger.info("Found refresh token in database for user: {}", storedToken.getTbuserId());

            // JWT 토큰 검증
            String userId = JWT.require(getTokenAlgorithm())
                    .build()
                    .verify(refreshToken)
                    .getClaim("id").asString();

            logger.info("JWT verification successful, user ID: {}", userId);

            // DB의 사용자 ID와 토큰의 사용자 ID 일치 여부 확인
            if (!userId.equals(storedToken.getTbuserId())) {
                logger.error("Token user ID mismatch. Token: {}, Stored: {}",
                        userId, storedToken.getTbuserId());
                throw new InvalidTokenException("Token user mismatch");
            }

            return userId;
        } catch (JWTVerificationException e) {
            logger.error("JWT verification failed", e);
            throw e;
        } catch (Exception e) {
            logger.error("Refresh token verification failed", e);
            throw new InvalidTokenException("Invalid refresh token");
        }
    }


    @Override
    // Access Token 발급을 위한 함수. refreshToken이 유효하다면 Access Token 발급한다.
    public String issueAccessToken(String refreshToken) throws JWTVerificationException {

        // Refresh Token 검증(실패시 throws JWTVerificationException)
        System.out.println("refresh : " +refreshToken);
        String tbuserId = verifyRefreshToken(refreshToken);

        // Access Token 생성
        String accessToken = createAccessToken(tbuserId);

        return accessToken;
    }
}
