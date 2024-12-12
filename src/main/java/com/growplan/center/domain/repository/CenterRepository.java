package com.growplan.center.domain.repository;

import com.growplan.center.domain.Center;
import com.growplan.child.domain.UserChild;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<UserChild, Long> {

    @Query("""
            SELECT c
            FROM Center c
            LEFT JOIN FETCH CenterTag ct
           """)
    List<Center> findAllByPageable(final Pageable pageable);

    Long countCenter();
}
