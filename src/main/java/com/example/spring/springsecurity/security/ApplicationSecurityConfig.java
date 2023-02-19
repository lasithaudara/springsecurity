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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

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
                    .anyRequest().authenticated()
                    .and()
                        .formLogin().loginPage("/login").permitAll()
                        .defaultSuccessUrl("/management/api/v1/students")
                        .usernameParameter("customusername")
                        .passwordParameter("custompassword")
                    .and()
                    .rememberMe() //to enable remember me option
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("something robust")
                        .rememberMeParameter("customrememberme")
                    .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        //we can customize the logout with any HTTP url
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login");

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
