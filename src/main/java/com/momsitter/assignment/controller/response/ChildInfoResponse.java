package com.momsitter.assignment.controller.response;

import static java.util.stream.Collectors.toList;

import com.momsitter.assignment.domain.Child;
import java.time.LocalDate;
import java.util.List;

public class ChildInfoResponse {

    private final Long number;
    private final LocalDate dateOfBirth;
    private final String gender;

    public ChildInfoResponse(Long number, LocalDate dateOfBirth, String gender) {
        this.number = number;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static List<ChildInfoResponse> of(List<Child> children) {
        return children.stream()
            .map(ChildInfoResponse::from)
            .collect(toList());
    }

    private static ChildInfoResponse from(Child child) {
        return new ChildInfoResponse(
            child.getNumber(),
            child.getDateOfBirth(),
            child.getGender()
        );
    }

    public Long getNumber() {
        return number;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
