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

    public Parent(Member member, String requestInfo) {
        this(null, member, new RequestInfo(requestInfo));
    }

    public Parent(Long number, Member member, RequestInfo requestInfo) {
        this.number = number;
        this.member = member;
        this.requestInfo = requestInfo;
    }

    public Long getNumber() {
        return number;
    }

    public String getRequestInfo() {
        return requestInfo.getValue();
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
