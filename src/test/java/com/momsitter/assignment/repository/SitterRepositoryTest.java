package com.momsitter.assignment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Sitter;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class SitterRepositoryTest {

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member를 통해 Sitter를 조회한다.")
    @Test
    void findByMember() {
        // given
        Member member = memberRepository.save(new Member(
            "최현구",
            LocalDate.of(1996, 2, 7),
            "남",
            "hyeon9mak",
            "abc123!@#",
            "hyeon9mak@mfort.co.kr"
        ));

        Sitter sitter = sitterRepository.save(new Sitter(
            member,
            3,
            5,
            "잘 놀아줘요."
        ));

        // when
        Optional<Sitter> foundSitter = sitterRepository.findByMember(member);

        // then
        assertThat(foundSitter).isPresent();
        assertThat(foundSitter.orElseThrow()).isEqualTo(sitter);
    }
}
