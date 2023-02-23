package com.example.spring.springsecurity.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {
    @Autowired
    private JwtConfig jwtConfig;
    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtConfig.secretKey().getBytes());
    }
}
