package com.growplan.child.domain.repository;

import com.growplan.child.domain.UserChild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<UserChild, Long> {
}
