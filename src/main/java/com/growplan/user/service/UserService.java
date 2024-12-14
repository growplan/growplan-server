package com.growplan.user.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.UserListResponse;
import com.growplan.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.growplan.common.code.ExceptionCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        return UserResponse.of(user);
    }

    public UserListResponse getUsers() {
        final List<User> users = userRepository.findAll();

        return UserListResponse.of(users);
    }

    public void updateUser(final Long userId, final UserUpdateRequest userUpdateRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        user.updateUser(userUpdateRequest.getNickname());

        userRepository.save(user);
    }

    public void deleteAccount(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        userRepository.deleteById(user.getId());
    }
}
