package com.growplan.center.domain.repository;

import com.growplan.center.domain.Center;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("""
            SELECT DISTINCT c
            FROM Center c
            LEFT JOIN c.centerTags ct
            LEFT JOIN ct.developmentType d
            LEFT JOIN c.scraps s
            WHERE (:centerTags IS NULL OR ct.developmentType.type IN :centerTags)
            AND (:locationQuery IS NULL OR c.location LIKE %:locationQuery%)
            AND (
                :isScraped IS NULL OR :isScraped = false OR EXISTS (
                    SELECT 1
                    FROM Scrap sc
                    WHERE sc.user.id = :userId AND sc.center = c
                )
            )
            """)
    List<Center> findFilteredCentersByPageable(
            @Param("centerTags") List<String> centerTags,
            @Param("locationQuery") String locationQuery,
            @Param("userId") Long userId,
            @Param("isScraped") Boolean isScraped,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(DISTINCT c)
            FROM Center c
            LEFT JOIN c.centerTags ct
            LEFT JOIN ct.developmentType d
            LEFT JOIN c.scraps s
            WHERE (:centerTags IS NULL OR ct.developmentType.type IN :centerTags)
            AND (:locationQuery IS NULL OR c.location LIKE %:locationQuery%)
            AND (
                :isScraped = false OR EXISTS (
                    SELECT 1
                    FROM Scrap sc
                    WHERE sc.user.id = :userId AND sc.center = c
                )
            )
            """)
    long countFilteredCenters(@Param("centerTags") List<String> centerTags,
                              @Param("locationQuery") String locationQuery,
                              @Param("userId") Long userId,
                              @Param("isScraped") Boolean isScraped
    );
}
