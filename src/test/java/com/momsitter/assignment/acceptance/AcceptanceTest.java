package com.momsitter.assignment.acceptance;

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

    protected ExtractableResponse<Response> postRequestWithBody(String path, Object object) {
        return RestAssured.given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(object)
            .when().post(path)
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> getRequestWithToken(String path, String token) {
        return RestAssured.given()
            .log().all()
            .auth().oauth2(token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get(path)
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> putRequestWithBodyAndToken(
        String path,
        Object object,
        String token
    ) {
        return RestAssured.given()
            .log().all()
            .auth().oauth2(token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(object)
            .when().put(path)
            .then().log().all()
            .extract();
    }
}
