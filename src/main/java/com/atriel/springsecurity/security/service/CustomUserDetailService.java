package com.atriel.springsecurity.security.service;

import com.atriel.springsecurity.security.dao.UserDao;
import com.atriel.springsecurity.security.vo.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUserDetail user = userDao.loginID(username);
        if(username==null) {
            throw new UsernameNotFoundException(username);
        }

        System.out.println("CustomUserDetailsService 성공");
        return user;
    }

}
