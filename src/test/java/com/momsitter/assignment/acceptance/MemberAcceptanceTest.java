package com.momsitter.assignment.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.momsitter.assignment.controller.request.ChildInfoRequest;
import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.controller.request.ParentInfoRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.request.UpdateInfoRequest;
import com.momsitter.assignment.controller.response.LoginResponse;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.exception.ExceptionResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("POST /api/members/create-sitter - 정상적인 경우 시터 회원가입에 성공한다.")
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
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/create-sitter", request);

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("POST /api/members/create-sitter - 잘못된 정보가 포함될 경우 시터 회원가입에 실패한다.")
    @Test
    void sitterNameNullOrBlankException() {
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
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/create-sitter", request);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("PUT /api/members/add-sitter - 정상적인 경우 멤버에게 시터역할을 부여한다.")
    @Test
    void memberGetSitterRole() {
        // given
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            parentInfo
        );

        ExtractableResponse<Response> parentResponse = postRequestWithBody("/api/members/create-parent", request);

        // when
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "pw123!@#");
        SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-sitter",
            sitterInfo,
            token
        );

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("PUT /api/members/add-sitter - 이미 시터로 등록된 사용자의 경우 예외가 발생한다.")
    @Test
    void memberGetSitterRoleException() {
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

        ExtractableResponse<Response> sitterResponse = postRequestWithBody("/api/members/create-sitter", request);

        // when
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "pw123!@#");
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-sitter",
            sitterInfo,
            token
        );

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("PUT /api/members/add-sitter - 유효하지 않은 토큰의 경우 예외가 발생한다.")
    @Test
    void addSitterNotFoundMemberException() {
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

        ExtractableResponse<Response> sitterResponse = postRequestWithBody("/api/members/create-sitter", request);

        // when
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-sitter",
            sitterInfo,
            "invalid token"
        );

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("POST /api/members/create-parent - 정상적인 경우 부모 회원가입에 성공한다.")
    @Test
    void createParentSuccess() {
        // given
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            parentInfo
        );

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/create-parent", request);

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("POST /api/members/create-parent - 잘못된 정보가 포함될 경우 부모 회원가입에 실패한다.")
    @Test
    void parentNameNullOrBlankException() {
        // given
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            " ",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            parentInfo
        );

        // when
        ExtractableResponse<Response> response = postRequestWithBody("/api/members/create-parent", request);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("PUT /api/members/add-parent - 정상적인 경우 멤버에게 부모역할을 부여한다.")
    @Test
    void memberGetParentRole() {
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

        ExtractableResponse<Response> parentResponse = postRequestWithBody("/api/members/create-sitter", request);

        // when
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "pw123!@#");
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-parent",
            parentInfo,
            token
        );

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("PUT /api/members/add-parent - 이미 부모로 등록된 사용자의 경우 예외가 발생한다.")
    @Test
    void memberPutParentRoleException() {
        // given
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            parentInfo
        );

        ExtractableResponse<Response> parentResponse = postRequestWithBody("/api/members/create-parent", request);

        // when
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "pw123!@#");
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-parent",
            parentInfo,
            token
        );

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("PUT /api/members/add-parent - 유효하지 않은 토큰의 경우 예외가 발생한다.")
    @Test
    void addParentNotFoundMemberException() {
        // given
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            "hyeon9mak",
            "pw123!@#",
            "email@email.com",
            parentInfo
        );

        ExtractableResponse<Response> parentResponse = postRequestWithBody("/api/members/create-parent", request);

        // when
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/add-parent",
            parentInfo,
            "invalid token"
        );

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("GET /api/members/me - 로그인 후 회원정보를 조회한다.")
    @Test
    void findInfoOfMineWithToken() {
        // given
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");
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
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", "이상한값" + token);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    @DisplayName("GET /api/members/me - 시터로만 가입한 회원은 시터 정보만 조회가 가능하다.")
    @Test
    void onlySitter() {
        // given
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", token);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(memberResponse.getSitterInfo()).isNotNull();
        assertThat(memberResponse.getParentInfo()).isNull();
    }

    @DisplayName("GET /api/members/me - 부모로만 가입한 회원은 부모 정보만 조회가 가능하다.")
    @Test
    void onlyParent() {
        // given
        ID_PASSWORD로_부모_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", token);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(memberResponse.getSitterInfo()).isNull();
        assertThat(memberResponse.getParentInfo()).isNotNull();
    }

    @DisplayName("GET /api/members/me - 시터와 부모로 가입한 회원은 모든 정보 조회가 가능하다.")
    @Test
    void all() {
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");
        토큰과_함께_부모역할을_추가한다(token);

        // when
        ExtractableResponse<Response> response = getRequestWithToken("/api/members/me", token);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(memberResponse.getSitterInfo()).isNotNull();
        assertThat(memberResponse.getParentInfo()).isNotNull();
    }

    @DisplayName("PUT /api/members/me - 회원의 이메일과 비밀번호를 변경할 수 있다.")
    @Test
    void updateInfo() {
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");
        String token = 로그인후_토큰을_발급받는다("hyeon9mak", "password123!@#");

        // when
        UpdateInfoRequest request = new UpdateInfoRequest("qwer!@#123", "jjii@naver.com");
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/me",
            request,
            token
        );

        // then
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(memberResponse.getEmail()).isEqualTo(request.getEmail());
    }

    @DisplayName("PUT /api/members/me - 유효하지 않은 토큰으로 회원 정보변경 요청시 예외가 발생한다.")
    @Test
    void updateInfoException() {
        ID_PASSWORD로_시터_회원가입을_진행한다("hyeon9mak", "password123!@#");

        // when
        UpdateInfoRequest request = new UpdateInfoRequest("qwer!@#123", "jjii@naver.com");
        ExtractableResponse<Response> response = putRequestWithBodyAndToken(
            "/api/members/me",
            request,
            "올바르지 않은 토큰"
        );

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(response.as(ExceptionResponse.class)).isNotNull();
    }

    private void ID_PASSWORD로_시터_회원가입을_진행한다(String id, String password) {
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

    private void ID_PASSWORD로_부모_회원가입을_진행한다(String id, String password) {
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );
        CreateParentRequest request = new CreateParentRequest(
            "최현구",
            LocalDate.now(),
            "남",
            id,
            password,
            "email@email.com",
            parentInfo
        );

        postRequestWithBody("/api/members/create-parent", request);
    }

    private void 토큰과_함께_부모역할을_추가한다(String token) {
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );

        putRequestWithBodyAndToken("/api/members/add-parent", parentInfo, token);
    }

    private String 로그인후_토큰을_발급받는다(String id, String password) {
        LoginRequest request = new LoginRequest(id, password);

        return postRequestWithBody("/api/login", request).as(LoginResponse.class).getAccessToken();
    }
}
