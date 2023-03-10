package com.example.spring.springsecurity.security;

import com.example.spring.springsecurity.auth.ApplicationUserDetailsService;
import com.example.spring.springsecurity.jwt.JwtConfig;
import com.example.spring.springsecurity.jwt.JwtTokenVerifier;
import com.example.spring.springsecurity.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

import static com.example.spring.springsecurity.security.ApplicationUserRoles.STUDENT;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SecretKey secretKey;
    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .authenticationProvider(daoAuthenticationProvider())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(
                        authenticationManager(
                                http.getSharedObject(AuthenticationConfiguration.class)), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/", "index", "/css/*").permitAll()
                .requestMatchers(" /api/**").hasRole(STUDENT.name())
                .anyRequest().authenticated();



        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
        return daoAuthenticationProvider;
    }
}
