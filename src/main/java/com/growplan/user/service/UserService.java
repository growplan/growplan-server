package com.growplan.user.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.login.domain.UserSign;
import com.growplan.login.dto.request.SignUpRequest;
import com.growplan.login.dto.response.SignUpResponse;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import com.growplan.user.domain.repository.UserSignRepository;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.UserListResponse;
import com.growplan.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserSignRepository userSignRepository;

    public UserResponse getUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER));

        return UserResponse.of(user);
    }

    public UserListResponse getUsers() {
        final List<User> users = userRepository.findAll();

        return UserListResponse.of(users);
    }

    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getBirthdate(),
                signUpRequest.getEmail(),
                signUpRequest.getNumber()
        );

        final User savedUser = userRepository.save(user);

        final UserSign userSign = new UserSign(
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                savedUser
        );

        userSignRepository.save(userSign);

        return SignUpResponse.of(savedUser);
    }

    public void updateUser(final Long userId, final UserUpdateRequest userUpdateRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER));

        user.updateUser(
                userUpdateRequest.getName(),
                userUpdateRequest.getBirthdate(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getNumber()
        );

        userRepository.save(user);
    }

    public void deleteAccount(final Long userId) {
        // TODO 연관 관계 찾아서 모두 삭제
        userRepository.deleteById(userId);
    }
}
