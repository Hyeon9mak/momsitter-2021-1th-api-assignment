package com.momsitter.assignment.domain;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Child {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Embedded
    private DateOfBirth dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "parent_number")
    private Parent parent;

    protected Child() {
    }

    public Child(LocalDate dateOfBirth, String gender) {
        this(null, new DateOfBirth(dateOfBirth), Gender.findByName(gender));
    }

    public Child(
        Long number,
        DateOfBirth dateOfBirth,
        Gender gender
    ) {
        this.number = number;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public void matchParent(Parent parent) {
        this.parent = parent;
    }

    public Long getNumber() {
        return number;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.getValue();
    }

    public String getGender() {
        return gender.name();
    }

    public Parent getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Child child = (Child) o;
        return Objects.equals(number, child.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
