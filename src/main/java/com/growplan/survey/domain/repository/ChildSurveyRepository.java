package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.ChildSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ChildSurveyRepository extends JpaRepository<ChildSurvey, Long> {

    @Query("""
            SELECT cs FROM ChildSurvey cs
            LEFT JOIN FETCH cs.survey s
            LEFT JOIN FETCH s.surveyGroup sg
            WHERE s.validAge >= :childAge AND DATE(cs.updatedAt) = :currentDate
            """)
    List<ChildSurvey> findByValidAge(@Param("childAge") final Double childAge, @Param("currentDate") final LocalDate date);

    @Query("""
            SELECT cs FROM ChildSurvey cs
            LEFT JOIN FETCH cs.survey s
            LEFT JOIN FETCH s.surveyGroup.developmentType d
            WHERE cs.userChild.id = :childId AND DATE(cs.updatedAt) = :currentDate AND d.type = :developmentType
            """)
    List<ChildSurvey> findByChildIdAndDevelopmentType(
            @Param("currentDate") final LocalDate date,
            @Param("developmentType") final String developmentType,
            @Param("childId") final Long childId
    );
}