package com.momsitter.assignment.authorization;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationConfig implements WebMvcConfigurer {

    // TODO: AuthService 구현체 대신 인터페이스로 사용할 수 있도록 생각해보기.
    // TODO: 의존하는 방향에 대해 직접 그림으로 그려봐야 정확한 판단이 가능할듯!
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationConfig(
        AuthService authService,
        JwtTokenProvider jwtTokenProvider
    ) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createAuthMemberArgumentResolver());
    }

    @Bean
    public AuthMemberArgumentResolver createAuthMemberArgumentResolver() {
        return new AuthMemberArgumentResolver(authService, jwtTokenProvider);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns("*");
    }
}
