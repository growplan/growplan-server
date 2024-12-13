package com.growplan.center.domain.repository;

import com.growplan.center.domain.Center;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("""
             SELECT c
             FROM Center c
             LEFT JOIN FETCH CenterTag ct
            """)
    List<Center> findAllByPageable(final Pageable pageable);

    @Query("""
            SELECT COUNT(c)
            FROM Center c
            LEFT JOIN FETCH c.centerTags ct
            WHERE (:centerTags IS NULL OR ct.developmentType.type IN :centerTags)
            AND (:province IS NULL OR c.location LIKE %:province%)
            AND (:city IS NULL OR c.location LIKE %:city%)
            AND (:neighborhood IS NULL OR c.location LIKE %:neighborhood%)
            """)
    Long countFilteredCenters(
            @Param("centerTags") final List<String> centerTags,
            @Param("province") final String province,
            @Param("city") final String city,
            @Param("neighborhood") final String neighborhood);
}
