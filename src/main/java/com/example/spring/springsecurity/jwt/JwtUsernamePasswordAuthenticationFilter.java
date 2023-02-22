package com.example.spring.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final  AuthenticationManager authenticationManager;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(), //username is the principle
                    authenticationRequest.password());  //password is the credential

            return authenticationManager.authenticate(authentication);  //This will authenticate the username and password
                                                                        //whether this user exist
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* **** If 'attemptAuthentication(---)' fail this method will never call **** */

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // this method is to send a token to client if the request authentication successful

        String key = "dasksdalsazxlckv2320384uskaddnz123udsan";
        String token = Jwts.builder()
                .setSubject(authResult.getName())                                               // PAYLOAD {
                .claim("authorities", authResult.getAuthorities())                           //   ..
                .setIssuedAt(new Date())                                                       //    ..
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2))) //  {
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))                                 //   VERIFY SIGNATURE {..}
                .compact();

       response.addHeader("Authorization", "Bearer"+token);
    }
}
