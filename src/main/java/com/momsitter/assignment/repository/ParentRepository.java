package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByMember(Member member);
}
