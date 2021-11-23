package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Password 단위 테스트")
class PasswordTest {

    @DisplayName("Password를 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new Password(null))
                .isExactlyInstanceOf(InvalidPasswordException.class);
        }

        @DisplayName("value가 공백이면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new Password(" "))
                .isExactlyInstanceOf(InvalidPasswordException.class);
        }

        @DisplayName("value의 길이가 4 미만이면 예외가 발생한다.")
        @Test
        void minLengthException() {
            // when, then
            assertThatThrownBy(() -> new Password("p1!"))
                .isExactlyInstanceOf(InvalidPasswordException.class);
        }

        @DisplayName("value의 길이가 20 초과면 예외가 발생한다.")
        @Test
        void maxLengthException() {
            // when, then
            assertThatThrownBy(() -> new Password("pw1!askdlkfajsdlkjfasdfzxcv"))
                .isExactlyInstanceOf(InvalidPasswordException.class);
        }

        @DisplayName("value에 문자, 숫자, 특수문자가 모두 포함되지 않으면 예외가 발생한다.")
        @Test
        void patternException() {
            // given
            String 글자_숫자 = "word123";
            String 글자_특수문자 = "word!@#";
            String 숫자_특수문자 = "123!@#";
            String 모두포함 = "word123!@#";

            // when, then
            assertThatThrownBy(() -> new Password(글자_숫자))
                .isExactlyInstanceOf(InvalidPasswordException.class);

            assertThatThrownBy(() -> new Password(글자_특수문자))
                .isExactlyInstanceOf(InvalidPasswordException.class);

            assertThatThrownBy(() -> new Password(숫자_특수문자))
                .isExactlyInstanceOf(InvalidPasswordException.class);

            assertThatCode(() -> new Password(모두포함))
                .doesNotThrowAnyException();
        }
    }
}
