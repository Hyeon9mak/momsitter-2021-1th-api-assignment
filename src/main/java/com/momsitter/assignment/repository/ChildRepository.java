package com.momsitter.assignment.repository;

import com.momsitter.assignment.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

}
