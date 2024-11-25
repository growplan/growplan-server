package com.growplan.child.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.child.domain.type.GenderType;
import com.growplan.child.dto.request.ChildRequest;
import com.growplan.child.dto.response.ChildListResponse;
import com.growplan.child.dto.response.ChildResponse;
import com.growplan.common.exception.BadRequestException;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.growplan.common.code.ExceptionCode.USER_CHILD_NOT_FOUND;
import static com.growplan.common.code.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;

    public ChildResponse getChild(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        return ChildResponse.of(userChild);
    }

    public ChildListResponse getChildren(final Long userId) {
        final List<UserChild> userChildren = childRepository.findByUserId(userId);

        return ChildListResponse.of(userChildren);
    }

    public void saveChild(final Long userId, final ChildRequest childRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        final UserChild userChild = new UserChild(
                childRequest.getName(),
                childRequest.getBirthdate(),
                GenderType.of(childRequest.getGender()),
                childRequest.getBornHeight(),
                childRequest.getBornWeight(),
                childRequest.getIsPremature(),
                childRequest.getBirthWeeks(),
                user
        );

        childRepository.save(userChild);
    }

    public void updateChild(final Long userId, final Long childId, final ChildRequest childRequest) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        userChild.updateUserChild(
                childRequest.getName(),
                childRequest.getBirthdate(),
                GenderType.of(childRequest.getGender()),
                childRequest.getBornHeight(),
                childRequest.getBornWeight(),
                childRequest.getIsPremature(),
                childRequest.getBirthWeeks()
        );

        childRepository.save(userChild);
    }

    public void deleteChild(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        childRepository.delete(userChild);
    }
}
