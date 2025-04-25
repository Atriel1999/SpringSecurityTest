package com.atriel.springsecurity.security.dao;

import com.atriel.springsecurity.security.vo.Authority;
import com.atriel.springsecurity.security.vo.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CustomUserDetail loginID(String id) {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            CustomUserDetail user = new CustomUserDetail();
            // ResultSet의 컬럼들을 CustomUserDetail 필드에 매핑
            user.setId(rs.getString("ID"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setAUTHORITY(Authority.valueOf(rs.getString("AUTHORITY")));
            // 필요한 다른 필드들도 매핑
            return user;
        });
    }
}