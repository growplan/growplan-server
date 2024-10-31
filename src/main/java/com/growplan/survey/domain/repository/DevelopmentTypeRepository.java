package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.DevelopmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevelopmentTypeRepository extends JpaRepository<DevelopmentType, Long> {

    List<DevelopmentType> findAllByType(final List<String> developmentTypes);
}
