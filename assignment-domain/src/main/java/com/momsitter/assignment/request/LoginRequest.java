package com.momsitter.assignment.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "회원 아이디는 Null 이거나 공백일 수 없습니다.")
    private String id;

    @NotBlank(message = "회원 비밀번호는 Null 이거나 공백일 수 없습니다.")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
