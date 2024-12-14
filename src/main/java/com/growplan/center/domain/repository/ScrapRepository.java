package com.growplan.center.domain.repository;

import com.growplan.center.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Query("""
            SELECT s FROM Scrap s
            WHERE s.user.id = :userId AND s.center.id = :centerId
            """)
    Optional<Scrap> findByUserIdAndCenterId(@Param("userId") final Long userId, @Param("centerId") final Long centerId);
}
