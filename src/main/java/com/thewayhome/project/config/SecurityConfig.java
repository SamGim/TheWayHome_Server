package com.thewayhome.project.config;

import com.thewayhome.project.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {


    @Bean
    public JwtTokenFilter jwtTokenFilter(AuthenticationManager authenticationManager) {
        return new JwtTokenFilter(authenticationManager);
    }

    @Bean
    public SnsAuthenticationFilter SnsAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new SnsAuthenticationFilter(authenticationManager);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(request -> request.getServletPath().startsWith("/resources/"))
                .requestMatchers(request -> request.getServletPath().startsWith("/api-docs/"))
                .requestMatchers(request -> request.getServletPath().startsWith("/swagger-ui/**"))
                .requestMatchers(request -> request.getServletPath().startsWith("/register"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            JwtAuthenticationProvider jwtTokenProvider,
            KakaoAuthenticationProvider kakaoAuthenticationProvider,
            NaverAuthenticationProvider naverAuthenticationProvider,
            GoogleAuthenticationProvider googleAuthenticationProvider
    ) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .parentAuthenticationManager(null)
                .authenticationProvider(jwtTokenProvider)
                .authenticationProvider(kakaoAuthenticationProvider)
                .authenticationProvider(naverAuthenticationProvider)
                .authenticationProvider(googleAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtTokenFilter jwtTokenFilter,
            SnsAuthenticationFilter SnsAuthenticationFilter
    ) throws Exception { //  초기화 단계에서 HttpSecurity 객체가 실제 설정한 필터를 생성
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/**")
                            .permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
//                .exceptionHandling(exceptionHandling -> {
//                    exceptionHandling
//                            .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                            .accessDeniedHandler(new JwtAccessDeniedHandler());
//                })
                .sessionManagement(sessionManagement -> {
                    sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(SnsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
