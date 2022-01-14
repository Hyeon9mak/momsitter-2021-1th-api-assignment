package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidRequestInfoException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RequestInfo {

    @NotNull
    @Column(name = "request_info")
    private String value;
    // TODO: 스트링 말고 다른 자료구조로 변경

    protected RequestInfo() {
    }

    public RequestInfo(String value) {
        this.value = value;
        validateNull(this.value);
        validateBlank(this.value);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidRequestInfoException("신청 내용은 Null 일 수 없습니다.");
        }
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new InvalidRequestInfoException("신청 내용은 공백으로 이루어질 수 없습니다.");
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
        RequestInfo that = (RequestInfo) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
