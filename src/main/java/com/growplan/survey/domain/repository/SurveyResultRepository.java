package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {

    @Query("""
            SELECT sr FROM SurveyResult sr
            WHERE sr.createdAt = (
                SELECT MAX(s.createdAt) FROM SurveyResult s
            ) AND sr.userChild.id = :childId
            """)
    List<SurveyResult> findRecentSurveyResults(@Param("childId") final Long childId);

    @Query("""
            SELECT sr FROM SurveyResult sr
            WHERE DATE(sr.updatedAt) = :currentDate AND sr.developmentType.type = :developmentType
            """)
    Optional<SurveyResult> findByDateAndDevelopmentType(
            @Param("currentDate") final LocalDate date,
            @Param("developmentType") final String developmentType
    );
}
