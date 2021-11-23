package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Sitter;
import java.time.LocalDate;

public class MemberResponse {

    private final Long number;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String id;
    private final String password;
    private final String email;
    private final SitterInfoResponse sitterInfo;

    public MemberResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String password,
        String email,
        SitterInfoResponse sitterInfo
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.email = email;
        this.sitterInfo = sitterInfo;
    }

    public static MemberResponse from(Member member, Sitter sitter) {
        return new MemberResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getPassword(),
            member.getEmail(),
            SitterInfoResponse.from(sitter)
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }
}
