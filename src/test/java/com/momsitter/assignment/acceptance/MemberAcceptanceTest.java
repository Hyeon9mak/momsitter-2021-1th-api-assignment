package com.momsitter.assignment.acceptance;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
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

        // when, then
        Post요청으로_Created_상태코드와_결과를_반환받는다("/api/members/sitter", request);
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

        // when, then
        Post요청으로_BadRequest_상태코드와_예외를_반환받는다("/api/members/sitter", request);
    }
}
