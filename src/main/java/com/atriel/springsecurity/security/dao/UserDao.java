package com.atriel.springsecurity.security.dao;

import com.atriel.springsecurity.security.vo.CustomUserDetail;





public interface UserDao {
    public CustomUserDetail loginID(String id);
}
