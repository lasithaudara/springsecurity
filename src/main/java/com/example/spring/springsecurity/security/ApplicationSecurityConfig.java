package com.example.spring.springsecurity.security;

import com.example.spring.springsecurity.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.spring.springsecurity.security.ApplicationUserRoles.STUDENT;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

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
                        .logoutSuccessUrl("/login")
                    .and()
                    .authenticationProvider(daoAuthenticationProvider());

            return http.build();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
            return daoAuthenticationProvider;
        }
    }
