package com.atriel.springsecurity.security.config;

import com.atriel.springsecurity.security.jwt.JwtAuthenticationFilter;
import com.atriel.springsecurity.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//참고자료: https://cbn1218.tistory.com/11
@Configuration
@EnableWebSecurity//(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .defaultSuccessUrl("/loginSuccess", true)
                        .permitAll()
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/sign-in", "/", "/login").permitAll()
                        .anyRequest().authenticated())
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)               // ALWAYS: 항상 HTTP세션을 사용 (전통적인 웹로그인 (form login))
                .build();                                                         // IF_REQUIRED: 필요할때만 세션사용 (대부분 웹 애플리케이션 사용[Default])
                                                                                  // STATELESS: 세션 절대생성X, 기존에 존재해도 사용안함
                                                                                  // STATELESS는 보통 JWT, Bearer토큰, Restful서버, 모바일앱 백엔드등 사용
    }
}
