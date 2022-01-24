package com.momsitter.assignment.response;

public class LoginResponse {

    private String accessToken;

    // 테스트 코드에서 리플렉션을 사용함
    protected LoginResponse() {
    }

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
