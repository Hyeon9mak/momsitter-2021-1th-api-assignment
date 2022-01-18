package com.momsitter.assignment.controller.request;

import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Sitter;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateSitterRequest {

    @NotBlank(message = "회원 이름은 Null 이거나 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "회원 생년월일은 Null 일 수 없습니다.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "회원 성별은 Null 이거나 공백일 수 없습니다.")
    private String gender;

    @NotBlank(message = "회원 아이디는 Null 이거나 공백일 수 없습니다.")
    private String id;

    @NotBlank(message = "회원 비밀번호는 Null 이거나 공백일 수 없습니다.")
    private String password;

    @NotNull(message = "회원 이메일은 Null 일 수 없습니다.")
    @Email(message = "회원 이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotNull(message = "시터 정보는 Null 일 수 없습니다.")
    private SitterInfoRequest sitterInfo;

    protected CreateSitterRequest() {
    }

    public CreateSitterRequest(
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String password,
        String email,
        SitterInfoRequest sitterInfo
    ) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.email = email;
        this.sitterInfo = sitterInfo;
    }

    public Member toMember() {
        return new Member(
            name,
            dateOfBirth,
            gender,
            id,
            password,
            email
        );
    }

    public Sitter toSitter() {
        return sitterInfo.toSitter();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public SitterInfoRequest getSitterInfo() {
        return sitterInfo;
    }
}
