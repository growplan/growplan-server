package com.growplan.login.domain.repository;

import com.growplan.login.domain.UserSign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSignRepository extends JpaRepository<UserSign, Long> {

    Optional<UserSign> findByUsername(final String username);
}
