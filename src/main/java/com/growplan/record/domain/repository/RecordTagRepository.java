package com.growplan.record.domain.repository;

import com.growplan.record.domain.ChildRecordTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordTagRepository extends JpaRepository<ChildRecordTag, Long> {

}
