package com.momsitter.assignment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.momsitter.assignment.domain.Member;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail() {
        // given
         Member member = memberRepository.save(new Member(
            "최현구",
            LocalDate.of(1996, 2, 7),
            "남",
            "hyeon9mak",
            "abc123!@#",
            "hyeon9mak@mfort.co.kr"
        ));

        // when
        Optional<Member> foundMember = memberRepository.findByEmail(member.getEmail());

        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getNumber()).isEqualTo(member.getNumber());
    }
}