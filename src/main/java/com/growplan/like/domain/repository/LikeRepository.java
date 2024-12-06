package com.growplan.like.domain.repository;

import com.growplan.like.domain.RecordLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<RecordLike, Long> {

    @Query("""
            SELECT l FROM RecordLike l
            LEFT JOIN FETCH l.user u
            LEFT JOIN FETCH l.childRecord cr
            WHERE cr.id = :recordId AND u.id = :userId
            """)
    Optional<RecordLike> findByUserIdAndRecordId(@Param("userId") final Long userId, @Param("recordId") final Long recordId);
}