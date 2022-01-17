package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Member;
import java.time.LocalDate;

public class MemberResponse {

    private final Long number;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String id;
    private final String email;

    public MemberResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.email = email;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmail()
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
}
