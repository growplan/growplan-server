package com.growplan.center.service;

import com.growplan.center.domain.Center;
import com.growplan.center.domain.CenterScrap;
import com.growplan.center.domain.repository.CenterRepository;
import com.growplan.center.domain.repository.ScrapRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.growplan.common.code.ExceptionCode.CENTER_NOT_FOUND;
import static com.growplan.common.code.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {

    private final CenterRepository centerRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    public void saveScrap(final Long userId, final Long centerId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        final Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new BadRequestException(CENTER_NOT_FOUND));

        scrapRepository.findByUserIdAndCenterId(userId, centerId)
                .ifPresentOrElse(
                        existingScrap -> scrapRepository.delete(existingScrap),
                        () -> scrapRepository.save(new CenterScrap(user, center))
                );
    }
}
