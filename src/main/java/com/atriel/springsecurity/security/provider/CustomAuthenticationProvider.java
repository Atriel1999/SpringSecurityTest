package com.atriel.springsecurity.security.provider;

import com.atriel.springsecurity.security.vo.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username=(String) authentication.getPrincipal();
        String password=(String) authentication.getCredentials();

        CustomUserDetail user = new CustomUserDetail();

        try {
            user = (CustomUserDetail) userDetailsService.loadUserByUsername(username);
        } catch(EmptyResultDataAccessException e){
            throw new BadCredentialsException("존재하지 않는 유저입니다");
        }

        if(!matchPassword(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호 오류");
        }

        return new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private boolean matchPassword(String loginPwd, String password) {
        return loginPwd.equals(password);
    }

}
