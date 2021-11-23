package com.momsitter.assignment.controller.response;

public class LoginResponse {

    private String accessToken;

    protected LoginResponse() {
    }

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
