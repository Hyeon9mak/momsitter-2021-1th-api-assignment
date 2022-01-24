package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidDateOfBirthException;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class DateOfBirth {

    @NotNull
    @Column(name = "dateOfBirth")
    private LocalDate value;

    protected DateOfBirth() {
    }

    public DateOfBirth(LocalDate value) {
        this.value = value;
        validateNull(this.value);
        validateToday(this.value);
    }

    private void validateNull(LocalDate value) {
        if (Objects.isNull(value)) {
            throw new InvalidDateOfBirthException("회원의 생년월일은 Null 일 수 없습니다.");
        }
    }

    private void validateToday(LocalDate value) {
        if (value.isAfter(LocalDate.now())) {
            throw new InvalidDateOfBirthException(String.format("%s 는 유효하지 않은 회원의 생년월일 입니다.", value));
        }
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateOfBirth that = (DateOfBirth) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
