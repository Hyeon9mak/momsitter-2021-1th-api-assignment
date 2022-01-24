package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MemberId 단위 테스트")
class IdTest {

    @DisplayName("MemberId를 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new Id(null))
                .isExactlyInstanceOf(InvalidIdException.class);
        }

        @DisplayName("value가 공백이면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new Id(" "))
                .isExactlyInstanceOf(InvalidIdException.class);
        }

        @DisplayName("value의 길이가 3 미만이면 예외가 발생한다.")
        @Test
        void minLengthException() {
            // when, then
            assertThatThrownBy(() -> new Id("두근"))
                .isExactlyInstanceOf(InvalidIdException.class);
        }

        @DisplayName("value의 길이가 10 초과면 예외가 발생한다.")
        @Test
        void maxLengthException() {
            // when, then
            assertThatThrownBy(() -> new Id("맘시터과제하는중임하하"))
                .isExactlyInstanceOf(InvalidIdException.class);
        }
    }
}
