package com.momsitter.assignment.request;

import com.momsitter.assignment.domain.Password;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateInfoRequest {

    @NotBlank(message = "회원 비밀번호는 Null 이거나 공백일 수 없습니다.")
    private String password;

    @NotNull(message = "회원 이메일은 Null 일 수 없습니다.")
    @Email(message = "회원 이메일 형식이 유효하지 않습니다.")
    private String email;

    protected UpdateInfoRequest() {
    }

    public UpdateInfoRequest(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public Password toPassword() {
        return new Password(password);
    }

    public com.momsitter.assignment.domain.Email toEmail() {
        return new com.momsitter.assignment.domain.Email(email);
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
