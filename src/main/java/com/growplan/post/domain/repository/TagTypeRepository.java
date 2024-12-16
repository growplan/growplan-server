package com.growplan.post.domain.repository;

import com.growplan.post.domain.TagType;
import com.growplan.survey.domain.DevelopmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagTypeRepository extends JpaRepository<TagType, Long> {

    List<TagType> findByTypeIn(final List<String> types);
}
