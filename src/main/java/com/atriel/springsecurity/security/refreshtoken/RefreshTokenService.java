package com.atriel.springsecurity.security.refreshtoken;

import com.atriel.springsecurity.model.repository.entity.User;
import com.atriel.springsecurity.security.jwt.JwtTokenProvider;
import com.atriel.springsecurity.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-token.expiry-ms}")
    private Long refreshTokenExpiryMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Long getRefreshTokenExpiryMs() {
        return refreshTokenExpiryMs;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiryMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException("Invalid refresh token: " + token));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken() + " Refresh token was expired. Please make a new sign in request");
        }

        return token;
    }

    @Transactional
    public String refreshAccessToken(String refreshToken) {
        RefreshToken token = findByToken(refreshToken);
        token = verifyExpiration(token);
        User user = token.getUser();

        // User 객체 정보로 Authentication 객체 생성
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getName(), null, List.of(new SimpleGrantedAuthority(user.getAuthority().name())));

        return jwtTokenProvider.generateToken(authentication);
    }
}