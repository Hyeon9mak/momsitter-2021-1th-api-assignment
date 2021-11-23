package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidDateOfBirthException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("DateOfBirth 단위 테스트")
class DateOfBirthTest {

    @DisplayName("DateOfBirth를 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new DateOfBirth(null))
                .isExactlyInstanceOf(InvalidDateOfBirthException.class);
        }

        @DisplayName("value가 오늘보다 늦다면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new DateOfBirth(LocalDate.MAX))
                .isExactlyInstanceOf(InvalidDateOfBirthException.class);
        }
    }
}
