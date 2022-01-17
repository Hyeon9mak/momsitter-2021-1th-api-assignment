package com.momsitter.assignment.authorization;

import com.momsitter.assignment.service.AuthService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationInterceptor authorizationInterceptor;

    public AuthorizationConfig(
        AuthService authService,
        JwtTokenProvider jwtTokenProvider,
        AuthorizationInterceptor authorizationInterceptor
    ) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationInterceptor = authorizationInterceptor;
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("/api/members/me")
            .addPathPatterns("/api/members/add-sitter")
            .addPathPatterns("/api/members/add-parent");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns("*");
    }
}
