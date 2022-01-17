package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Child;
import com.momsitter.assignment.domain.Parent;
import java.time.LocalDate;
import java.util.List;

public class ParentResponse {

    private final Long number;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String id;
    private final String email;
    private final ParentInfoResponse parentInfo;

    public ParentResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email,
        ParentInfoResponse parentInfo
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.email = email;
        this.parentInfo = parentInfo;
    }

    public static ParentResponse from(Parent parent, List<Child> children) {
        return new ParentResponse(
            parent.getMember().getNumber(),
            parent.getMember().getName(),
            parent.getMember().getDateOfBirth(),
            parent.getMember().getGender(),
            parent.getMember().getId(),
            parent.getMember().getEmail(),
            ParentInfoResponse.from(parent, children)
        );
    }

    public Long getNumber() {
        return number;
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

    public String getEmail() {
        return email;
    }

    public ParentInfoResponse getParentInfo() {
        return parentInfo;
    }
}