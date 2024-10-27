package com.growplan.child.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.child.domain.type.GenderType;
import com.growplan.child.dto.request.ChildRequest;
import com.growplan.common.exception.BadRequestException;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;

    public void saveChild(final Long userId, final ChildRequest childRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER));

        final UserChild userChild = new UserChild(
                childRequest.getName(),
                childRequest.getBirthdate(),
                GenderType.of(childRequest.getGender()),
                childRequest.getBornHeight(),
                childRequest.getBornHeight(),
                user
        );

        childRepository.save(userChild);
    }
}
