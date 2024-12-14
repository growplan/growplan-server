package com.growplan.center.domain.repository;

import com.growplan.center.domain.Center;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("""
             SELECT c
             FROM Center c
             LEFT JOIN FETCH c.centerTags ct
             LEFT JOIN FETCH c.scraps s
             LEFT JOIN FETCH ct.developmentType d
            """)
    List<Center> findAllByPageable(final Pageable pageable);

    List<Center> findAll();
}
