package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Sitter;
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

    public static SitterResponse from(Sitter sitter) {
        return new SitterResponse(
            sitter.getMember().getNumber(),
            sitter.getMember().getName(),
            sitter.getMember().getDateOfBirth(),
            sitter.getMember().getGender(),
            sitter.getMember().getId(),
            sitter.getMember().getEmail(),
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

    public String getEmail() {
        return email;
    }

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }
}
