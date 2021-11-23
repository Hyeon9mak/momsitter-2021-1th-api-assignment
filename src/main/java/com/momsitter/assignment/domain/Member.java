package com.momsitter.assignment.domain;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

@Entity
public class Member {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Embedded
    private Name name;

    @Embedded
    private DateOfBirth dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Id id;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Sitter sitter;

    protected Member() {
    }

    public Member(String name, LocalDate dateOfBirth, String gender, String id, String password, String email) {
        this(
            null,
            new Name(name),
            new DateOfBirth(dateOfBirth),
            Gender.findByName(gender),
            new Id(id),
            new Password(password),
            new Email(email)
        );
    }

    public Member(
        Long number,
        Name name,
        DateOfBirth dateOfBirth,
        Gender gender,
        Id id,
        Password password,
        Email email
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public void getJob(Sitter sitter) {
        this.sitter = sitter;
        this.sitter.mappedBy(this);
    }

    public Long getNumber() {
        return number;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.getValue();
    }

    public String getGender() {
        return gender.name();
    }

    public String getId() {
        return id.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public Sitter getSitter() {
        return sitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(number, member.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
