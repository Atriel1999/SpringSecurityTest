package com.atriel.springsecurity.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}

//Bearer 인증방식 사용 (가장 직관적이고 널리 사용됨)
// -> Access Token을 Http 요청의 Authorization Header에 포함해서 전송
// ex) Authorization: Bearer <access_token>

// 랜덤 암호키 생성 명령어 (Unix 계열 전용)
// openssl rand -hex 32
// 토큰의 암호화 복호화에 사용할 키, 여러알고리즘 중 HS256사용 (32글자)
