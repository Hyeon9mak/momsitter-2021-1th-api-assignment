package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterRepository extends JpaRepository<Sitter, Long> {

}
