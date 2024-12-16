package com.growplan.record.domain.repository;

import com.growplan.record.domain.ChildRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<ChildRecord, Long> {

    @Query("""
            SELECT cr
            FROM ChildRecord cr
            LEFT JOIN FETCH cr.userChild uc
            LEFT JOIN FETCH cr.recordTags rt
            LEFT JOIN FETCH rt.developmentType d
            LEFT JOIN FETCH cr.childRecordImages i
            WHERE cr.userChild.user.id = :userId AND cr.userChild.id = :childId
            """)
    List<ChildRecord> findRecordsByUserIdAndChildId(@Param("userId") final Long userId, @Param("childId") final Long childId);

    @Query("""
            SELECT cr
            FROM ChildRecord cr
            LEFT JOIN FETCH cr.userChild uc
            LEFT JOIN FETCH cr.recordTags rt
            LEFT JOIN FETCH cr.childRecordImages i
            WHERE cr.userChild.id = :childId AND cr.id = :recordId
            """)
    Optional<ChildRecord> findByChildIdAndRecordId(@Param("childId") final Long childId, @Param("recordId") final Long recordId);

    @Query("""
            SELECT cr
            FROM ChildRecord cr
            LEFT JOIN FETCH cr.userChild u
            LEFT JOIN FETCH u.user
            WHERE u.id = :userId AND cr.id = :recordId
            """)
    Optional<ChildRecord> findByUserIdAndRecordId(@Param("userId") final Long userId, @Param("recordId") final Long recordId);
}
