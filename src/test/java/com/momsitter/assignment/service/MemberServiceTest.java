package com.momsitter.assignment.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.ChildInfoRequest;
import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.ParentInfoRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.ChildInfoResponse;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.controller.response.ParentResponse;
import com.momsitter.assignment.controller.response.SitterResponse;
import com.momsitter.assignment.exception.AlreadyParentMemberException;
import com.momsitter.assignment.exception.AlreadySitterMemberException;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.ChildRepository;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.repository.ParentRepository;
import com.momsitter.assignment.repository.SitterRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @DisplayName("모든 정보가 전달되었을 경우 시터 회원이 정상적으로 생성된다.")
    @Test
    void createSitter() {
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
        SitterResponse response = memberService.createMemberAndAddSitterRole(request);

        // then
        assertThat(memberRepository.findById(response.getNumber())).isPresent();
        assertThat(sitterRepository.findById(response.getSitterInfo().getNumber())).isPresent();
    }

    @DisplayName("모든 정보가 전달되었을 경우 부모 회원이 정상적으로 생성된다.")
    @Test
    void createParent() {
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
        ParentResponse response = memberService.createMemberAndAddParentRole(request);

        // then
        assertThat(memberRepository.findById(response.getNumber())).isPresent();
        assertThat(parentRepository.findById(response.getParentInfo().getNumber())).isPresent();
        List<Long> childNumbers = response.getParentInfo()
            .getChildInfos()
            .stream()
            .map(ChildInfoResponse::getNumber)
            .collect(toList());
        assertThat(childRepository.findAllById(childNumbers)).hasSize(childNumbers.size());
    }

    @DisplayName("회원에 시터 역할을 추가할 때")
    @Nested
    class AddSitterRole {

        @DisplayName("시터 정보가 모두 전달되었을 경우 정상적으로 추가된다.")
        @Test
        void success() {
            // given
            ParentResponse parentResponse = 부모_회원정보를_생성한다();

            // when
            SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");
            SitterResponse response = memberService.createAndAddSitterRole(
                parentResponse.getNumber(),
                sitterInfo
            );

            // then
            assertThat(response.getNumber()).isEqualTo(parentResponse.getNumber());
            assertThat(response.getSitterInfo()).usingRecursiveComparison()
                .ignoringFields("number")
                .isEqualTo(sitterInfo);
        }

        @DisplayName("이미 시터로 등록된 회원일 경우 예외가 발생한다.")
        @Test
        void alreadySitterMember() {
            // given
            SitterResponse sitterResponse = 시터_회원정보를_생성한다();
            SitterInfoRequest sitterInfo = new SitterInfoRequest(3, 5, "진짜 잘해요.");

            // when, then
            assertThatThrownBy(() -> memberService.createAndAddSitterRole(
                sitterResponse.getNumber(),
                sitterInfo
            )).isExactlyInstanceOf(AlreadySitterMemberException.class);
        }
    }

    @DisplayName("회원에 부모 역할을 추가할 때")
    @Nested
    class AddParentRole {

        @DisplayName("부모 정보가 모두 전달되었을 경우 정상적으로 추가된다.")
        @Test
        void success() {
            // given
            SitterResponse sitterResponse = 시터_회원정보를_생성한다();

            // when
            ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
            ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
            ParentInfoRequest parentInfo = new ParentInfoRequest(
                "잘 봐주세요.",
                Arrays.asList(childInfo1, childInfo2)
            );

            ParentResponse response = memberService.createAndAddParentRole(
                sitterResponse.getNumber(),
                parentInfo
            );

            // then
            assertThat(response.getNumber()).isEqualTo(sitterResponse.getNumber());
            assertThat(response.getParentInfo().getRequestInfo()).isEqualTo(parentInfo.getRequestInfo());
            assertThat(response.getParentInfo().getChildInfos()).hasSize(parentInfo.getChildInfos().size());
        }

        @DisplayName("이미 부모로 등록된 회원일 경우 예외가 발생한다.")
        @Test
        void alreadySitterMember() {
            // given
            ParentResponse parentResponse = 부모_회원정보를_생성한다();
            ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
            ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
            ParentInfoRequest parentInfo = new ParentInfoRequest(
                "잘 봐주세요.",
                Arrays.asList(childInfo1, childInfo2)
            );

            // when, then
            assertThatThrownBy(() -> memberService.createAndAddParentRole(
                parentResponse.getNumber(),
                parentInfo
            )).isExactlyInstanceOf(AlreadyParentMemberException.class);
        }
    }

    @DisplayName("회원 정보를 조회할 때")
    @Nested
    class MemberInfo {

        @DisplayName("number에 일치하는 회원이 존재하는 경우 조회할 수 있다.")
        @Test
        void success() {
            // given
            SitterResponse sitterResponse = 시터_회원정보를_생성한다();
            AuthMemberDto authMember = new AuthMemberDto(sitterResponse.getNumber());

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

    private SitterResponse 시터_회원정보를_생성한다() {
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

        return memberService.createMemberAndAddSitterRole(request);
    }

    private ParentResponse 부모_회원정보를_생성한다() {
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

        return memberService.createMemberAndAddParentRole(request);
    }
}
