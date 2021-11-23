package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Id;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Password;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndPassword(Id id, Password password);

    Optional<Member> findById(Id id);
}
