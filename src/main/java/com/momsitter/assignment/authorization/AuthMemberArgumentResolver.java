package com.momsitter.assignment.authorization;

import com.momsitter.assignment.exception.AuthorizationException;
import com.momsitter.assignment.service.AuthService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthMemberArgumentResolver(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        String token = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));

        if (isInvalidToken(token)) {
            throw new AuthorizationException("로그인 정보가 유효하지 않습니다.");
        }

        return authService.findAuthMemberByToken(token);
    }

    private boolean isInvalidToken(String token) {
        return Objects.isNull(token) || token.isEmpty() || !jwtTokenProvider.validateToken(token);
    }
}
