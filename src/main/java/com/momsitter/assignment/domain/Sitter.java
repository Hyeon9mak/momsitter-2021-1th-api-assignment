package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidCareAgeException;
import java.util.Objects;
import javax.persistence.Embedded;
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

    @Embedded
    private Introduction introduction;

    @OneToOne
    @JoinColumn(name = "member_number")
    private Member member;

    protected Sitter() {
    }

    public Sitter(int minCareAge, int maxCareAge, String introduction) {
        this(null, minCareAge, maxCareAge, new Introduction(introduction));
    }

    public Sitter(Long number, int minCareAge, int maxCareAge, Introduction introduction) {
        this.number = number;
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.introduction = introduction;
        validateCareAge(this.minCareAge, this.maxCareAge);
    }

    private void validateCareAge(int minCareAge, int maxCareAge) {
        validateNegative(this.minCareAge);
        validateNegative(this.maxCareAge);

        if (minCareAge > maxCareAge) {
            throw new InvalidCareAgeException(
                String.format("케어 가능 최소 연령 %d는 최대연령 %d보다 클 수 없습니다.", minCareAge, maxCareAge)
            );
        }
    }

    private void validateNegative(int careAge) {
        if (careAge < 0) {
            throw new InvalidCareAgeException(String.format("케어 가능 연령 %d는 음수일 수 없습니다.", careAge));
        }
    }

    public void mappedBy(Member member) {
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
        return introduction.getValue();
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
