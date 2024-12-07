package com.thc.projectcd_spring.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExternalProperties {

    @Value("${jwt.tokenSecretKey}")
    private String tokenSecretKey;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.accessTokenExpirationTime}")
    private Integer accessTokenExpirationTime;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Value("${jwt.refreshTokenExpirationTime}")
    private Integer refreshTokenExpirationTime;

}
