package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.SurveyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {

    // TODO max min 추가
    @Query("""
            SELECT sg FROM SurveyGroup sg
            LEFT JOIN FETCH sg.surveys s
            WHERE s.validAge >= :validAge
            """)
    List<SurveyGroup> findSurveyGroupByValidAge(@Param("validAge") final Double validAge);
}
