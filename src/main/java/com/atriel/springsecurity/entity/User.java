package com.atriel.springsecurity.security.entity;

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

    @Column(nullable = true) // 프로필 사진 URL은 필수가 아닐 수 있으므로 nullable = true 설정
    private String picture;  // 프로필 사진 URL

    // Google API 관련 정보 (별도 엔티티 또는 JSON 형태로 저장 고려)
    // private String googleAccessToken;
    // private String googleRefreshToken;

    // 기타 필요한 정보

    // 생성자, 메서드 등
}