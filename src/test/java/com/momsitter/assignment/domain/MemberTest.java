package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 단위 테스트")
class MemberTest {

    @DisplayName("Member가 Sitter로 등록된다.")
    @Test
    void getJob() {
        // given
        Member member = new Member(
            "최현구",
            LocalDate.of(2022, 1, 10),
            "남",
            "hyeon9mak",
            "abc123!",
            "hyeon9mak@mfort.co.kr"
        );

        // when
        Sitter sitter = new Sitter(member, 3, 5, "잘해요");

        // then
        assertThat(sitter.getMember()).isEqualTo(member);
    }

    @DisplayName("Member가 Parent로 등록된다.")
    @Test
    void registration() {
        // given
        Member member = new Member(
            "최현구",
            LocalDate.of(2022, 1, 10),
            "남",
            "hyeon9mak",
            "abc123!",
            "hyeon9mak@mfort.co.kr"
        );

        // when
        Parent parent = new Parent(member, "잘 돌봐주세요");

        // then
        assertThat(parent.getMember()).isEqualTo(member);
    }

    @DisplayName("Member가 Sitter와 Parent로 동시에 등록될 수 있다.")
    @Test
    void registrationAndGetJob() {
        // given
        Member member = new Member(
            "최현구",
            LocalDate.of(2022, 1, 10),
            "남",
            "hyeon9mak",
            "abc123!",
            "hyeon9mak@mfort.co.kr"
        );

        // when
        Sitter sitter = new Sitter(member, 3, 5, "잘해요");
        Parent parent = new Parent(member, "잘 돌봐주세요");

        // then
        assertThat(sitter.getMember()).isEqualTo(member);
        assertThat(parent.getMember()).isEqualTo(member);
    }
}