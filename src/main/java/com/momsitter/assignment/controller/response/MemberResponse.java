package com.momsitter.assignment.controller.response;

import com.momsitter.assignment.domain.Member;
import java.time.LocalDate;

public class MemberResponse {

    private Long number;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String id;
    private String email;
    private SitterInfoResponse sitterInfo;
    private ParentInfoResponse parentInfo;

    // 인수 테스트에서 필요로 함
    protected MemberResponse() {
    }

    private MemberResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email,
        SitterInfoResponse sitterInfo
    ) {
        this(
            number,
            name,
            dateOfBirth,
            gender,
            id,
            email,
            sitterInfo,
            null
        );
    }

    private MemberResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email,
        ParentInfoResponse parentInfo
    ) {
        this(
            number,
            name,
            dateOfBirth,
            gender,
            id,
            email,
            null,
            parentInfo
        );
    }

    private MemberResponse(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String email,
        SitterInfoResponse sitterInfo,
        ParentInfoResponse parentInfo
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.email = email;
        this.sitterInfo = sitterInfo;
        this.parentInfo = parentInfo;
    }

    public static MemberResponse sitter(Member member) {
        return new MemberResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmailValue(),
            SitterInfoResponse.from(member.getSitter())
        );
    }

    public static MemberResponse parent(Member member) {
        return new MemberResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmailValue(),
            ParentInfoResponse.from(member.getParent(), member.getChildren())
        );
    }

    public static MemberResponse all(Member member) {
        return new MemberResponse(
            member.getNumber(),
            member.getName(),
            member.getDateOfBirth(),
            member.getGender(),
            member.getId(),
            member.getEmailValue(),
            SitterInfoResponse.from(member.getSitter()),
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

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }

    public ParentInfoResponse getParentInfo() {
        return parentInfo;
    }
}
