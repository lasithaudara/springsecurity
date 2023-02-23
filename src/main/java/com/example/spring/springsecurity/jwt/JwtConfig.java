package com.example.spring.springsecurity.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.jwt")
public record JwtConfig(String secretKey, String tokenPrefix, Integer tokenExpDays) {
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
