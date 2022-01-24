package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidIntroductionException;
import com.sun.istack.NotNull;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Introduction {

    @NotNull
    @Column(name = "introduction")
    private String value;
    // TODO: 스트링 말고 다른 자료구조로 변경

    protected Introduction() {
    }

    public Introduction(String value) {
        this.value = value;
        validateNull(this.value);
        validateBlank(this.value);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidIntroductionException("시터의 소개정보는 Null 일 수 없습니다.");
        }
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new InvalidIntroductionException("시터의 소개정보는 공백으로 이루어질 수 없습니다.");
        }
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
        Introduction that = (Introduction) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
