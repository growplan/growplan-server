package com.growplan.like.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.like.domain.RecordLike;
import com.growplan.like.domain.repository.LikeRepository;
import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.growplan.common.code.ExceptionCode.RECORD_NOT_FOUND;
import static com.growplan.common.code.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final RecordRepository recordRepository;

    public void toggleLike(final Long userId, final Long recordId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        likeRepository.findByUserIdAndRecordId(userId, recordId)
                .ifPresentOrElse(
                        like -> deleteLike(user, like),
                        () -> saveLike(user, recordId)
                );
    }

    private void saveLike(final User user, final Long recordId) {
        final ChildRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        final RecordLike recordLike = new RecordLike(user, record);
        likeRepository.save(recordLike);
    }

    private void deleteLike(final User user, final RecordLike recordLike) {
        user.getRecordLikes().remove(recordLike);
        likeRepository.delete(recordLike);
    }
}
