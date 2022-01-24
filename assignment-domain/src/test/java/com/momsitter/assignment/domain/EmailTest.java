package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Email 단위 테스트")
class EmailTest {

    @DisplayName("Email을 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new Email(null))
                .isExactlyInstanceOf(InvalidEmailException.class);
        }

        @DisplayName("value가 공백이면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new Email(" "))
                .isExactlyInstanceOf(InvalidEmailException.class);
        }

        @DisplayName("value가 이메일 형식에 맞지 않으면 예외가 발생한다.")
        @Test
        void patternException() {
            // given
            String 도메인 = "@gmail.com";
            String 이메일계정 = "email123";
            String 이메일계정_도메인 = "email123@gmail.com";

            // when, then
            assertThatThrownBy(() -> new Email(도메인))
                .isExactlyInstanceOf(InvalidEmailException.class);

            assertThatThrownBy(() -> new Email(이메일계정))
                .isExactlyInstanceOf(InvalidEmailException.class);

            assertThatCode(() -> new Email(이메일계정_도메인))
                .doesNotThrowAnyException();
        }
    }
}
