package com.momsitter.assignment.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Sitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private int minCareAge;

    private int maxCareAge;

    private String introduction;

    @OneToOne
    @JoinColumn(name = "member_number")
    private Member member;

    protected Sitter() {
    }

    public Sitter(int minCareAge, int maxCareAge, String introduction) {
        this(null, minCareAge, maxCareAge, introduction);
    }

    public Sitter(Long number, int minCareAge, int maxCareAge, String introduction) {
        this.number = number;
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.introduction = introduction;
    }

    public void join(Member member) {
        this.member = member;
    }

    public Long getNumber() {
        return number;
    }

    public int getMinCareAge() {
        return minCareAge;
    }

    public int getMaxCareAge() {
        return maxCareAge;
    }

    public String getIntroduction() {
        return introduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sitter sitter = (Sitter) o;
        return Objects.equals(number, sitter.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
