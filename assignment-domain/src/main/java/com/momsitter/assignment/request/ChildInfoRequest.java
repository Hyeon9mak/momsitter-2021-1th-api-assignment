package com.momsitter.assignment.request;

import com.momsitter.assignment.domain.Child;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChildInfoRequest {

    @NotNull(message = "아이의 생년월일은 Null 일 수 없습니다.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "아이의 성별은 Null 이거나 공백일 수 없습니다.")
    private String gender;

    protected ChildInfoRequest() {
    }

    public ChildInfoRequest(LocalDate dateOfBirth, String gender) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Child toChild() {
        return new Child(dateOfBirth, gender);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
