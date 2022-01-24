package com.momsitter.assignment.controller;

import com.momsitter.assignment.request.LoginRequest;
import com.momsitter.assignment.response.LoginResponse;
import com.momsitter.assignment.service.AuthServiceImpl;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthServiceImpl authService;

    public LoginController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse token = authService.login(request);

        return ResponseEntity.ok(token);
    }
}
