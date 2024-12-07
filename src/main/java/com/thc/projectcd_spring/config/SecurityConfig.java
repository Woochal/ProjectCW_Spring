package com.thc.projectcd_spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.projectcd_spring.repository.TbuserRepository;
import com.thc.projectcd_spring.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity // Spring Security의 웹 보안 기능을 활성화
@EnableMethodSecurity // 메서드 수준의 보안을 활성화합니다. 이를 통해 @PreAuthorize와 같은 어노테이션을 사용할 수 있습니다.
@Configuration
public class SecurityConfig {
    private final TbuserRepository tbuserRepository;
    private final CorsFilterConfiguration corsFilterConfiguration;
    private final ObjectMapper objectMapper; // JSON 변환을 위한 Jackson ObjectMapper
    private final AuthService authService; // 토큰 팩토리라고 생각해주세요!
    private final ExternalProperties externalProperties;

    public SecurityConfig(TbuserRepository tbuserRepository, CorsFilterConfiguration corsFilterConfiguration, ObjectMapper objectMapper, AuthService authService
            , ExternalProperties externalProperties) {
        this.tbuserRepository = tbuserRepository;
        this.corsFilterConfiguration = corsFilterConfiguration;
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }

    // BCryptPasswordEncoder를 빈으로 등록하여 비밀번호 인코딩 사용
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


    //Spring Security 권한 설정
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/login",
//                                "/api/tbuser/logout",
                                "/api/tbuser/email",
                                "/api/tbuser/email_confirm",
                                "/api/tbuser/signup",
                                "/api/tbchicken/getAllChicken",
                                "/api/tbchicken/getChickenComments",
                                "/files/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsFilterConfiguration.corsFilter())
                .apply(new CustomDsl());

        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                    authenticationManager, objectMapper, authService, externalProperties);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JwtAuthorizationFilter(
                            authenticationManager, tbuserRepository, authService, externalProperties))
                    .addFilterBefore(new FilterExceptionHandlerFilter(), BasicAuthenticationFilter.class);
        }
    }

}
