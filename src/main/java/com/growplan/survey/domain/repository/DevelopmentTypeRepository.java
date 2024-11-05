package com.growplan.survey.domain.repository;

import com.growplan.survey.domain.DevelopmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DevelopmentTypeRepository extends JpaRepository<DevelopmentType, Long> {

}
