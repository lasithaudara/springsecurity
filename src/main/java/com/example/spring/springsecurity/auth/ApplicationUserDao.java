package com.example.spring.springsecurity.auth;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> getUserByName(String userName);
}
