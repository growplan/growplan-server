package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.DevelopmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DevelopmentTypeRepository extends JpaRepository<DevelopmentType, Long> {

    List<DevelopmentType> findByTypeIn(final List<String> types);

    Optional<DevelopmentType> findByType(final String type);
}
