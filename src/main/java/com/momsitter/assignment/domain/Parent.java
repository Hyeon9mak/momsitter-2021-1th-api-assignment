package com.momsitter.assignment.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Parent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Embedded
    private RequestInfo requestInfo;

    @OneToOne
    @JoinColumn(name = "member_number")
    private Member member;

    protected Parent() {
    }

    public Parent(String requestInfo) {
        this(null, new RequestInfo(requestInfo));
    }

    public Parent(Long number, RequestInfo requestInfo) {
        this.number = number;
        this.requestInfo = requestInfo;
    }

    public void mappedBy(Member member) {
        this.member = member;
    }

    public Long getNumber() {
        return number;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parent parent = (Parent) o;
        return Objects.equals(number, parent.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
