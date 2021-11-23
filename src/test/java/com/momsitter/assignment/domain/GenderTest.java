package com.momsitter.assignment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.exception.InvalidGenderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Gender 단위 테스트")
class GenderTest {

    @DisplayName("Gender를 선택할 때 해당하는 성별이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void invalidException() {
        // when, then
        assertThatThrownBy(() -> Gender.findByName("없어 그런건"))
            .isExactlyInstanceOf(InvalidGenderException.class);
    }
}
