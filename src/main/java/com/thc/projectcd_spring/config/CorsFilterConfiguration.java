package com.thc.projectcd_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsFilterConfiguration {
    // CORS 필터를 정의하는 메서드
    @Bean
    public CorsFilter corsFilter() {

        System.out.println("CORS Filter executed");


        // URL 기반 CORS 설정 소스 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS 설정을 위한 CorsConfiguration 객체 생성
        CorsConfiguration config = new CorsConfiguration();

        // 클라이언트가 쿠키와 인증 정보를 포함할 수 있도록 허용
        config.setAllowCredentials(true);

        // 요청을 허용 주소 설정 (현재는 모든 도메인)
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // 모든 요청 헤더를 허용
//        config.addAllowedHeader("*");

        // 모든 HTTP 메서드를 허용 (GET, POST, PUT, DELETE 등)
        config.addAllowedMethod("*");

        // 추가로 허용할 헤더 정의
        String[] arrays = {"Authorization", "RefreshToken", "Content-Type"};

        // 위에서 정의한 특정 헤더를 허용 ({"Authorization", "RefreshToken"})
        config.setAllowedHeaders(Arrays.asList(arrays));

        // /api/** 패턴을 가진 요청에 대해 CORS 설정 등록. 해당 형식의 요청이 아니면 다 거부된다.
        source.registerCorsConfiguration("/api/**", config);

        // 설정된 CORS 구성을 사용하여 CorsFilter 객체 생성 및 반환
        return new CorsFilter(source);
    }
}
