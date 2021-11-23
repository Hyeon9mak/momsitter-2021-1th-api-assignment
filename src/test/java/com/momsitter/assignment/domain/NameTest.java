package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Name 단위 테스트")
class NameTest {

    @DisplayName("Name을 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new Name(null))
                .isExactlyInstanceOf(InvalidNameException.class);
        }

        @DisplayName("value가 공백이면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new Name(" "))
                .isExactlyInstanceOf(InvalidNameException.class);
        }
    }
}
