package com.momsitter.assignment.acceptance;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.momsitter.assignment.exception.ExceptionResponse;
import com.momsitter.assignment.request.CreateSitterRequest;
import com.momsitter.assignment.request.LoginRequest;
import com.momsitter.assignment.request.SitterInfoRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("로그인 인수 테스트")
public class LoginAcceptanceTest extends AcceptanceTest {

    @DisplayName("POST /api/login - 정상적인 경우 토큰을 발급한다.")
    @Test
    void createSitterSuccess() {
        // given
        ID_PW로_시터회원가입을_진행한다("hyeon9mak", "pw123!@#");
        LoginRequest request = new LoginRequest("hyeon9mak", "pw123!@#");

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/login", request);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(OK.value());
        Assertions.assertThat(response.body()).isNotNull();
    }

    @DisplayName("POST /api/login - ID나 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void nameNullOrBlankException() {
        // given
        ID_PW로_시터회원가입을_진행한다("hyeon9mak", "pw123!@#");
        LoginRequest request = new LoginRequest("hyeon9", "no123!@#");

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/login", request);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        Assertions.assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    private void ID_PW로_시터회원가입을_진행한다(String id, String password) {
        SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");
        CreateSitterRequest request = new CreateSitterRequest(
            "최현구",
            LocalDate.now(),
            "남",
            id,
            password,
            "email@email.com",
            sitterInfo
        );

        postRequestWithBody("/api/members/create-sitter", request);
    }
}
