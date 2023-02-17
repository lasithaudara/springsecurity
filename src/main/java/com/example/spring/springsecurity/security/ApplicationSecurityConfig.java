package com.example.spring.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.spring.springsecurity.security.ApplicationUserPermission.*;
import static com.example.spring.springsecurity.security.ApplicationUserRoles.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/", "index", "/css/*").permitAll()
                    .requestMatchers(" /api/**").hasRole(STUDENT.name())
//                    .requestMatchers(HttpMethod.DELETE,"management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                    .requestMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                    .requestMatchers(HttpMethod.PUT," management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                    .requestMatchers(HttpMethod.GET," /management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
            return http.build();
        }

        @Bean
        protected UserDetailsService userDetailsService () {
            UserDetails userLasi = User.builder()
                    .username("lasitha")
                    .password(passwordEncoder.encode("test123"))
                    .authorities(STUDENT.getGrantedAuthorities())
                    .build();

            UserDetails userKasun = User.builder()
                    .username("kasun")
                    .password(passwordEncoder.encode("test123"))
                    .authorities(ADMIN.getGrantedAuthorities())
                    .build();

            UserDetails userLinda = User.builder()
                    .username("linda")
                    .password(passwordEncoder.encode("test123"))
                    .authorities(ADMINTRAINEE.getGrantedAuthorities())
                    .build();

            return new InMemoryUserDetailsManager(userLasi, userKasun, userLinda);
        }
    }
