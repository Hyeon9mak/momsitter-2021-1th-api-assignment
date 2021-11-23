package com.momsitter.assignment.acceptance;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import java.time.LocalDate;
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

        // when, then
        Post요청으로_OK_상태코드와_결과를_반환받는다("/api/login", request);
    }

    @DisplayName("POST /api/login - ID나 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void nameNullOrBlankException() {
        // given
        ID_PW로_시터회원가입을_진행한다("hyeon9mak", "pw123!@#");
        LoginRequest request = new LoginRequest("hyeon9", "no123!@#");

        // when, then
        Post요청으로_BadRequest_상태코드와_예외를_반환받는다("/api/login", request);
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

        Post요청으로_Created_상태코드와_결과를_반환받는다("/api/members/sitter", request);
    }
}
