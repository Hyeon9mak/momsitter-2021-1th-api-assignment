package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
