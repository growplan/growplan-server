package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.Survey;
import com.growplan.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT s FROM Survey s
            WHERE s.validAge <= :childAge
            """)
    List<Survey> findByValidAgeLessThanOrEqualTo(@Param("childAge") final Double childAge);
}
