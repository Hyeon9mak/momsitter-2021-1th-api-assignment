package com.momsitter.assignment.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.exception.ExceptionResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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

        // when, then
        Created_상태코드와_결과를_반환받는다(request);
    }

    @DisplayName("POST /api/members/sitter - 회원 이름이 Null이거나 공백이라면 예외가 발생한다.")
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

        // when, then
        BadRequest_상태코드와_예외를_반환받는다(request);
    }

    private void Created_상태코드와_결과를_반환받는다(CreateSitterRequest request) {
        // when
        ExtractableResponse<Response> response = RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/members/sitter")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.body()).isNotNull();
    }

    private void BadRequest_상태코드와_예외를_반환받는다(CreateSitterRequest request) {
        // when
        ExtractableResponse<Response> response = RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/members/sitter")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }
}
