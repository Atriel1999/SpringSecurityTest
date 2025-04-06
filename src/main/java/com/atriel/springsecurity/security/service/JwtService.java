package com.atriel.springsecurity.security.service;

import com.atriel.springsecurity.security.jwt.JwtToken;

public interface JwtService {
    JwtToken signIn(String username, String password);
}
