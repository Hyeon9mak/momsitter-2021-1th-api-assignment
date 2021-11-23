package com.momsitter.assignment.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.momsitter.assignment.exception.ExceptionResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }


    protected void Post요청으로_OK_상태코드와_결과를_반환받는다(String path, Object request) {
        // when
        ExtractableResponse<Response> response = RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(path)
            .then().log().all()
            .statusCode(OK.value())
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body()).isNotNull();
    }

    protected void Post요청으로_Created_상태코드와_결과를_반환받는다(String path, Object request) {
        // when
        ExtractableResponse<Response> response = RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(path)
            .then().log().all()
            .statusCode(CREATED.value())
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.body()).isNotNull();
    }

    protected void Post요청으로_BadRequest_상태코드와_예외를_반환받는다(String path, Object request) {
        // when
        ExtractableResponse<Response> response = RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(path)
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }
}
