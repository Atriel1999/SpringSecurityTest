package com.atriel.springsecurity.oauth2.service;

import com.atriel.springsecurity.entity.User;
import com.atriel.springsecurity.model.repository.UserRepository;
import com.atriel.springsecurity.oauth2.user.GoogleOAuth2User; // Google OAuth2User 사용
import com.atriel.springsecurity.oauth2.user.GoogleOAuth2UserInfo;
import com.atriel.springsecurity.oauth2.user.OAuth2UserInfo;
import com.atriel.springsecurity.security.vo.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private User processOAuth2User(OAuth2User oAuth2User) {
        GoogleOAuth2UserInfo oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByGoogleId(oAuth2UserInfo.getId());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getEmail().equals(oAuth2UserInfo.getEmail())) {
                throw new RuntimeException("Email mismatch with existing account."); // 예외 처리 필요
            }
            user = updateUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserInfo);
        }

        return user;
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setGoogleId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setPicture(oAuth2UserInfo.getPictureUrl());
        user.setAuthority(Authority.ROLE_USER); // 기본 권한 설정
        return userRepository.save(user);
    }

    private User updateUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setPicture(oAuth2UserInfo.getPictureUrl());
        return userRepository.save(existingUser);
    }
}