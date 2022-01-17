package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidCareAgeException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Sitter 단위 테스트")
class SitterTest {

    @DisplayName("Sitter를 생성할 때")
    @Nested
    class Create {

        @DisplayName("케어 가능 연령이 음수일 경우 예외가 발생한다.")
        @Test
        void negativeCareAgeException() {
            // given
            Member member = 멤버를_생성한다();

            // when, then
            assertThatThrownBy(() -> new Sitter(member, -1, 10, "잘해요."))
                .isExactlyInstanceOf(InvalidCareAgeException.class);

            assertThatThrownBy(() -> new Sitter(member, 3, -1, "잘해요."))
                .isExactlyInstanceOf(InvalidCareAgeException.class);
        }

        @DisplayName("케어 가능 최소 연령이 최대 연령보다 클 경우 예외가 발생한다.")
        @Test
        void minMaxCareAgeReverseException() {
            // given
            Member member = 멤버를_생성한다();

            // when, then
            assertThatThrownBy(() -> new Sitter(member, 5, 4, "잘해요."))
                .isExactlyInstanceOf(InvalidCareAgeException.class);
        }
    }

    private Member 멤버를_생성한다() {
        return new Member(
            "최현구",
            LocalDate.of(2022, 1, 10),
            "남",
            "hyeon9mak",
            "abc123!",
            "hyeon9mak@mfort.co.kr"
        );
    }
}
