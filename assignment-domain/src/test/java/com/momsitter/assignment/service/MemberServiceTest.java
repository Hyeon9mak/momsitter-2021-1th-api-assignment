package com.momsitter.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.exception.AlreadyParentMemberException;
import com.momsitter.assignment.exception.AlreadySitterMemberException;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.request.ChildInfoRequest;
import com.momsitter.assignment.request.CreateParentRequest;
import com.momsitter.assignment.request.CreateSitterRequest;
import com.momsitter.assignment.request.ParentInfoRequest;
import com.momsitter.assignment.request.SitterInfoRequest;
import com.momsitter.assignment.request.UpdateInfoRequest;
import com.momsitter.assignment.response.MemberResponse;
import com.momsitter.assignment.response.ParentResponse;
import com.momsitter.assignment.response.SitterResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
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
        Optional<Member> member = memberRepository.findById(response.getNumber());
        assertThat(member).isPresent();
        assertThat(member.get().isSitter()).isTrue();
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
        Optional<Member> member = memberRepository.findById(response.getNumber());
        assertThat(member).isPresent();
        assertThat(member.get().isParent()).isTrue();
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
            MemberResponse response = memberService.findMemberInfo(authMember.getNumber());

            // then
            assertThat(memberRepository.findById(response.getNumber())).isPresent();
        }

        @DisplayName("number에 일치하는 회원이 없는 경우 예외가 발생한다.")
        @Test
        void notFoundException() {
            // given
            AuthMemberDto authMember = new AuthMemberDto(Long.MAX_VALUE);

            // when, then
            assertThatThrownBy(() -> memberService.findMemberInfo(authMember.getNumber()))
                .isExactlyInstanceOf(MemberNotFoundException.class);
        }

        @DisplayName("시터로만 가입한 회원은 시터 정보만 반환된다.")
        @Test
        void onlySitter() {
            // given
            SitterResponse sitterResponse = 시터_회원정보를_생성한다();
            AuthMemberDto authMember = new AuthMemberDto(sitterResponse.getNumber());

            // when
            MemberResponse memberResponse = memberService.findMemberInfo(authMember.getNumber());

            // then
            assertThat(memberResponse.getSitterInfo()).isNotNull();
            assertThat(memberResponse.getParentInfo()).isNull();
        }

        @DisplayName("부모로만 가입한 회원은 부모 정보만 반환된다.")
        @Test
        void onlyParent() {
            // given
            ParentResponse parentResponse = 부모_회원정보를_생성한다();
            AuthMemberDto authMember = new AuthMemberDto(parentResponse.getNumber());

            // when
            MemberResponse memberResponse = memberService.findMemberInfo(authMember.getNumber());

            // then
            assertThat(memberResponse.getSitterInfo()).isNull();
            assertThat(memberResponse.getParentInfo()).isNotNull();
        }

        @DisplayName("시터와 부모로 모두 가입한 회원은 모든 정보가 반환된다.")
        @Test
        void all() {
            // given
            SitterResponse sitterResponse = 시터_회원정보를_생성한다();
            ParentResponse parentResponse = 부모_역할을_추가한다(sitterResponse.getNumber());
            AuthMemberDto authMember = new AuthMemberDto(parentResponse.getNumber());

            // when
            MemberResponse memberResponse = memberService.findMemberInfo(authMember.getNumber());

            // then
            assertThat(memberResponse.getSitterInfo()).isNotNull();
            assertThat(memberResponse.getParentInfo()).isNotNull();
        }
    }

    @DisplayName("회원 정보를 업데이트 할 수 있다.")
    @Test
    void updateMemberInfo() {
        // given
        SitterResponse sitterResponse = 시터_회원정보를_생성한다();

        // when
        UpdateInfoRequest request = new UpdateInfoRequest("qwer!@#123", "jjii@naver.com");
        MemberResponse response = memberService.updateMemberInfo(
            sitterResponse.getNumber(),
            request
        );

        // then
        assertThat(response.getNumber()).isEqualTo(sitterResponse.getNumber());
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
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

    private ParentResponse 부모_역할을_추가한다(Long memberNumber) {
        ChildInfoRequest childInfo1 = new ChildInfoRequest(LocalDate.of(2020, 1, 10), "남");
        ChildInfoRequest childInfo2 = new ChildInfoRequest(LocalDate.of(2021, 3, 20), "여");
        ParentInfoRequest parentInfo = new ParentInfoRequest(
            "잘 봐주세요.",
            Arrays.asList(childInfo1, childInfo2)
        );

        return memberService.createAndAddParentRole(memberNumber, parentInfo);
    }
}
