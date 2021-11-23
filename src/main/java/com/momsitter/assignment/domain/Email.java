package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidEmailException;
import com.sun.istack.NotNull;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

    private static final String PATTERN = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";

    @NotNull
    @Column(name = "email")
    private String value;

    protected Email() {
    }

    public Email(String value) {
        this.value = value;
        validateNull(this.value);
        validateBlank(this.value);
        validatePattern(this.value);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidEmailException("회원의 이메일은 Null 일 수 없습니다.");
        }
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new InvalidEmailException("회원의 이메일은 공백으로 이루어질 수 없습니다.");
        }
    }

    private void validatePattern(String value) {
        if (value.matches(PATTERN)) {
            return;
        }

        throw new InvalidEmailException(String.format("회원의 이메일 %s 는 형식이 올바르지 않습니다.", value));
    }

    public String getValue() {
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
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
