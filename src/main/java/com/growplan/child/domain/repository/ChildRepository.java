package com.growplan.child.domain.repository;

import com.growplan.child.domain.UserChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<UserChild, Long> {

    List<UserChild> findByUserId(final Long userId);

    @Query("""
            SELECT c FROM UserChild c
            WHERE c.id = :childId AND c.user.id = :userId
            """)
    Optional<UserChild> findByUserIdAndChildId(@Param("userId") final Long userId, @Param("childId") final Long childId);
}
