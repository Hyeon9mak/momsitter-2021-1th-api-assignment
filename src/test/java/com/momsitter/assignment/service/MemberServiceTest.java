package com.momsitter.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.repository.SitterRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("MemberService 통합 테스트")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SitterRepository sitterRepository;

    @DisplayName("Sitter를 생성할 때")
    @Nested
    class CreateSitter {

        @DisplayName("모든 정보가 전달되었을 경우 정상적으로 생성된다.")
        @Test
        void success() {
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
            MemberResponse response = memberService.createSitter(request);

            // then
            assertThat(memberRepository.findById(response.getNumber())).isPresent();
        }
    }

    @DisplayName("회원 정보를 조회할 때")
    @Nested
    class MemberInfo {

        @DisplayName("number에 일치하는 회원이 존재하는 경우 조회할 수 있다.")
        @Test
        void success() {
            // given
            MemberResponse createdMemberResponse = 회원정보를_생성한다();
            AuthMemberDto authMember = new AuthMemberDto(createdMemberResponse.getNumber());

            // when
            MemberResponse response = memberService.findMemberInfo(authMember);

            // then
            assertThat(memberRepository.findById(response.getNumber())).isPresent();
        }

        @DisplayName("number에 일치하는 회원이 없는 경우 예외가 발생한다.")
        @Test
        void notFoundException() {
            // given
            AuthMemberDto authMember = new AuthMemberDto(Long.MAX_VALUE);

            // when, then
            assertThatThrownBy(() -> memberService.findMemberInfo(authMember))
                .isExactlyInstanceOf(MemberNotFoundException.class);
        }
    }

    private MemberResponse 회원정보를_생성한다() {
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

        return memberService.createSitter(request);
    }
}
