package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Member;
import java.time.LocalDate;

public class SitterResponse {

    private final Long number;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String id;
    private final String email;
    private final SitterInfoResponse sitterInfo;

    public SitterResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email,
        SitterInfoResponse sitterInfo
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.email = email;
        this.sitterInfo = sitterInfo;
    }

    public static SitterResponse from(Member member) {
        return new SitterResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmailValue(),
            SitterInfoResponse.from(member.getSitter())
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

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }
}
