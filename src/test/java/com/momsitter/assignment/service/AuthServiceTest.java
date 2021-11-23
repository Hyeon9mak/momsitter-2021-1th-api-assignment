package com.momsitter.assignment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.exception.LoginFailedException;
import com.momsitter.assignment.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("AuthService 통합 테스트")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("로그인을 진행할 때")
    @Nested
    class Login {

        @DisplayName("기존에 아이디, 비밀번호가 일치하는 유저가 없을 경우 예외가 발생한다.")
        @Test
        void notFoundMemberException() {
            // given
            LoginRequest request = new LoginRequest("testId1", "password1!");

            // when, then
            assertThatThrownBy(() -> authService.login(request))
                .isExactlyInstanceOf(LoginFailedException.class);
        }

        @DisplayName("아이디가 일치하지 않을 경우 예외가 발생한다.")
        @Test
        void idNotMatchedException() {
            // given
            Member member = ID_PW_Email로_회원을_생성한다("testId2", "password2!", "email2@gmail.com");
            LoginRequest request = new LoginRequest("999id", "password2!");

            // when, then
            assertThatThrownBy(() -> authService.login(request))
                .isExactlyInstanceOf(LoginFailedException.class);
        }

        @DisplayName("비밀번호가 일치하지 않을 경우 예외가 발생한다.")
        @Test
        void passwordNotMatchedException() {
            // given
            Member member = ID_PW_Email로_회원을_생성한다("testId3", "password3!", "email3@gmail.com");
            LoginRequest request = new LoginRequest("testId3", "99password!");

            // when, then
            assertThatThrownBy(() -> authService.login(request))
                .isExactlyInstanceOf(LoginFailedException.class);
        }
    }

    private Member ID_PW_Email로_회원을_생성한다(String id, String password, String email) {
        return memberRepository.save(new Member(
            "최현구",
            LocalDate.now(),
            "남",
            id,
            password,
            email
        ));
    }
}
