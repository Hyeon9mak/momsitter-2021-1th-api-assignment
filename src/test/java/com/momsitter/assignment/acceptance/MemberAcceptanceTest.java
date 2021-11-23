package com.momsitter.assignment.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.LoginResponse;
import com.momsitter.assignment.exception.ExceptionResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("POST /api/members/sitter - 정상적인 경우 회원가입에 성공한다.")
    @Test
    void createSitterSuccess() {
        // given
        SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");
        CreateSitterRequest request = new CreateSitterRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            sitterInfo
        );

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/sitter", request);

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("POST /api/members/sitter - 잘못된 정보가 포함될 경우 회원가입에 실패한다.")
    @Test
    void nameNullOrBlankException() {
        // given
        SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");
        CreateSitterRequest request = new CreateSitterRequest(
            " ",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            sitterInfo
        );

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/sitter", request);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("GET /api/members/me - 로그인 후 회원정보를 조회한다.")
    @Test
    void findInfoOfMineWithToken() {
        // given
        ID_PASSWORD로_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", token);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("GET /api/members/me - 유효하지 않은 토큰의 경우 예외가 발생한다.")
    @Test
    void notFoundMemberException() {
        // given
        ID_PASSWORD로_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", "이상한값" + token);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    private void ID_PASSWORD로_회원가입을_진행한다(String id, String password) {
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

        postRequestWithBody("/api/members/sitter", request);
    }

    private String 로그인후_토큰을_발급받는다(String id, String password) {
        LoginRequest request = new LoginRequest(id, password);

        return postRequestWithBody("/api/login", request).as(LoginResponse.class).getAccessToken();
    }
}
