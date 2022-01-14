package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidRequestInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RequestInfo 단위 테스트")
class RequestInfoTest {

    @DisplayName("RequestInfo를 생성할 때")
    @Nested
    class Create {

        @DisplayName("value가 Null이면 예외가 발생한다.")
        @Test
        void nullException() {
            // when, then
            assertThatThrownBy(() -> new RequestInfo(null))
                .isExactlyInstanceOf(InvalidRequestInfoException.class);
        }

        @DisplayName("value가 공백이면 예외가 발생한다.")
        @Test
        void blankException() {
            // when, then
            assertThatThrownBy(() -> new RequestInfo(" "))
                .isExactlyInstanceOf(InvalidRequestInfoException.class);
        }
    }
}