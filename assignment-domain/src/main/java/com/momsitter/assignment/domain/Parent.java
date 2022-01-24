package com.momsitter.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Child> children = new ArrayList<>();

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

    public void addChildren(List<Child> children) {
        for (Child child : children) {
            addChild(child);
        }
    }

    private void addChild(Child child) {
        children.add(child);
        child.matchParent(this);
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

    public List<Child> getChildren() {
        return children;
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
