package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("""
            SELECT s FROM Survey s
            LEFT JOIN FETCH s.surveyGroup sg
            WHERE sg.minMonth <= :months AND sg.maxMonth >= :months AND sg.developmentType.type = :developmentType
            """)
    List<Survey> findByMonthsAndDevelopmentType(@Param("months") final Integer months, @Param("developmentType") final String developmentType);

    List<Survey> findByIdIn(final List<Long> surveyIds);
}
