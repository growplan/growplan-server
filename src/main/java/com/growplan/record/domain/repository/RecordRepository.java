package com.growplan.record.domain.repository;

import com.growplan.record.domain.ChildRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<ChildRecord, Long> {

    @Query("""
            SELECT cr
            FROM ChildRecord cr
            LEFT JOIN FETCH cr.userChild uc
            LEFT JOIN FETCH cr.recordTags rt
            WHERE cr.userChild.user.id = :userId AND cr.userChild.id = :childId
            """)
    List<ChildRecord> findRecordsByUserIdAndChildId(@Param("userId") final Long userId, @Param("childId") final Long childId);
}
