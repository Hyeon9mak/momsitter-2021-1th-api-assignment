package com.momsitter.assignment.authorization;

public class AuthMemberDto {

    private final Long number;

    public AuthMemberDto(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }
}
