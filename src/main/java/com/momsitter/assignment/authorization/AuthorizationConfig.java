package com.momsitter.assignment.authorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    public AuthorizationConfig(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO: 제외시킬 API 추가
//        registry.addInterceptor(authenticationInterceptor)
//            .addPathPatterns("/api/**")
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns("*");
    }
}
