package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.ChildSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChildSurveyRepository extends JpaRepository<ChildSurvey, Long> {

    @Query("""
            SELECT cs FROM ChildSurvey cs
            LEFT JOIN FETCH cs.survey s
            LEFT JOIN FETCH s.developmentType
            WHERE s.validAge <= :childAge
            """)
    List<ChildSurvey> findByValidAge(@Param("childAge") final Double childAge);

    @Query("""
            SELECT cs FROM ChildSurvey cs
            LEFT JOIN FETCH cs.survey s
            LEFT JOIN FETCH s.developmentType d
            WHERE s.validAge <= :childAge AND d.type = :developmentType
            """)
    List<ChildSurvey> findByValidAgeAndDevelopmentType(@Param("childAge") final Double childAge, @Param("developmentType") final String developmentType);
}
