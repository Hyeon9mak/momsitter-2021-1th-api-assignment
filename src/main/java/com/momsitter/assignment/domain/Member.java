package com.momsitter.assignment.domain;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @NotNull
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String gender;

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String email;

    protected Member() {
    }

    public Member(String name, LocalDate dateOfBirth, String gender, String id, String password, String email) {
        this(null, name, dateOfBirth, gender, id, password, email);
    }

    public Member(
        Long number,
        String name,
        LocalDate dateOfBirth,
        String gender,
        String id,
        String password,
        String email
    ) {
        this.number = number;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.email = email;
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
