package com.momsitter.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.repository.SitterRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("MemberService 통합 테스트")
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
                "password",
                "email",
                sitterInfo
            );

            // when
            MemberResponse response = memberService.createSitter(request);

            // then
            assertThat(memberRepository.findById(response.getNumber())).isPresent();
            assertThat(sitterRepository.findById(response.getSitterInfo().getNumber())).isPresent();
        }
    }
}
