package com.thc.projectcd_spring.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

// TokenFactory다.
public interface AuthService {

    // JWT 토큰을 생성하기 위한 알고리즘을 반환하는 메서드
    Algorithm getTokenAlgorithm();

    // 주어진 사용자 ID로 액세스 토큰을 생성하는 메서드
    String createAccessToken(String tbuserId);

    // 주어진 액세스 토큰을 검증하고, 유효한 경우 사용자 ID를 반환하는 메서드
    String verifyAccessToken(String accessToken) throws JWTVerificationException;

    // 주어진 사용자 ID로 리프레시 토큰을 생성하는 메서드
    String createRefreshToken(String tbuserId);

    // 주어진 사용자 ID의 리프레시 토큰을 무효화하는 메서드
    void revokeRefreshToken(String tbuserId);

    // 주어진 리프레시 토큰을 검증하고, 유효한 경우 사용자 ID를 반환하는 메서드
    String verifyRefreshToken(String refreshToken) throws JWTVerificationException;

    // 주어진 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급하는 메서드
    String issueAccessToken(String refreshToken) throws JWTVerificationException;


}
