package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.AlreadyParentMemberException;
import com.momsitter.assignment.exception.AlreadySitterMemberException;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private Sitter sitter;

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private Parent parent;

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

    public void addRole(Sitter sitter) {
        if (Objects.nonNull(this.sitter)) {
            throw new AlreadySitterMemberException(
                String.format("%s 회원은 이미 시터로 등록된 회원입니다.", getNumber())
            );
        }

        this.sitter = sitter;
        this.sitter.mappedBy(this);
    }

    public void addRole(Parent parent) {
        if (Objects.nonNull(this.parent)) {
            throw new AlreadyParentMemberException(
                String.format("%s 회원은 이미 부모로 등록된 회원입니다.", getNumber())
            );
        }

        this.parent = parent;
        this.parent.mappedBy(this);
    }

    public void update(Password password, Email email) {
        this.password = password;
        this.email = email;
    }

    public boolean isSitter() {
        return Objects.nonNull(sitter);
    }

    public boolean isNotSitter() {
        return !isSitter();
    }

    public boolean isParent() {
        return Objects.nonNull(parent);
    }

    public boolean isNotParent() {
        return !isParent();
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

    public Email getEmail() {
        return email;
    }

    public String getEmailValue() {
        return email.getValue();
    }

    public Sitter getSitter() {
        return sitter;
    }

    public Parent getParent() {
        return parent;
    }

    public List<Child> getChildren() {
        return parent.getChildren();
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
