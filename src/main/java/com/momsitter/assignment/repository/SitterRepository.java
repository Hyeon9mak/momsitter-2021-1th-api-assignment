package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Sitter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterRepository extends JpaRepository<Sitter, Long> {

    Optional<Sitter> findByMember(Member member);
}
