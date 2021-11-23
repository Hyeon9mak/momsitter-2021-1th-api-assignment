package com.momsitter.assignment.authorization;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String ORIGIN = "Origin";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflight(request)) {
            return true;
        }

        String extractedToken = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.validateToken(extractedToken);
    }

    // CORS preflight 체크
    private boolean isPreflight(HttpServletRequest request) {
        return isOptionsMethod(request.getMethod()) && hasOrigin(request.getHeader(ORIGIN));
    }

    private boolean isOptionsMethod(String method) {
        HttpMethod options = HttpMethod.OPTIONS;
        return method.equalsIgnoreCase(options.name());
    }

    private boolean hasOrigin(String origin) {
        return Objects.nonNull(origin);
    }
}
