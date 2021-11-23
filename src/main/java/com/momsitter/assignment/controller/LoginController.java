package com.momsitter.assignment.controller;

import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.controller.response.LoginResponse;
import com.momsitter.assignment.service.AuthService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse token = authService.login(request);

        return ResponseEntity.ok().body(token);
    }
}
