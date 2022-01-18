package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Member;
import java.time.LocalDate;

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

    public static ParentResponse from(Member member) {
        return new ParentResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmailValue(),
            ParentInfoResponse.from(member.getParent(), member.getChildren())
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
