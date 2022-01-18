package com.momsitter.assignment.controller.request;

import com.momsitter.assignment.domain.Child;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateParentRequest {

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

    @NotNull(message = "부모 정보는 Null 일 수 없습니다.")
    private ParentInfoRequest parentInfo;

    protected CreateParentRequest() {
    }

    public CreateParentRequest(
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String password,
        String email,
        ParentInfoRequest parentInfo
    ) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.email = email;
        this.parentInfo = parentInfo;
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

    public Parent toParent() {
        return parentInfo.toParent();
    }

    public List<Child> toChildren() {
        return parentInfo.toChildren();
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

    public ParentInfoRequest getParentInfo() {
        return parentInfo;
    }
}
