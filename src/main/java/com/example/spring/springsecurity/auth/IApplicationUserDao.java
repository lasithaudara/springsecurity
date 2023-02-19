package com.example.spring.springsecurity.auth;

import com.example.spring.springsecurity.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.spring.springsecurity.security.ApplicationUserRoles.*;
@Repository
public class IApplicationUserDao implements ApplicationUserDao{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> getUserByName(String userName) throws UsernameNotFoundException{
        return getApplicationUsers().stream().filter(applicationUser -> userName.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        return Arrays.asList(
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        "kasun",
                        passwordEncoder.encode("test123"),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        ADMINTRAINEE.getGrantedAuthorities(),
                        "linda",
                        passwordEncoder.encode("test123"),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "lasitha",
                        passwordEncoder.encode("test123"),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
