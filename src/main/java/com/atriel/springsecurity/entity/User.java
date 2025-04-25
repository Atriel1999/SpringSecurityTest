package com.atriel.springsecurity.entity;

import com.atriel.springsecurity.security.vo.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String googleId; // Google 고유 사용자 ID (`sub`)

    @Column(nullable = false)
    private String name;     // 사용자 이름

    @Column(nullable = false, unique = true)
    private String email;    // 이메일 주소

    @Column(nullable = true)
    private String picture;  // 프로필 사진 URL

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private java.util.Date hire_date;

    @Column(nullable = true)
    private String etc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority; // 권한 정보 (Authority enum 사용)

    // OAuth2 사용자 정보 기반 생성자
    public User(String googleId, String name, String email, String picture, Authority authority) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authority = authority;
    }

    // 기존 정보 기반 생성자 (필요하다면)
    public User(String googleId, String name, String email, String picture, String gender, java.util.Date hire_date, String etc, Authority authority) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.hire_date = hire_date;
        this.etc = etc;
        this.authority = authority;
    }
}