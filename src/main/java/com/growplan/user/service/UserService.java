package com.growplan.user.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.login.domain.UserSign;
import com.growplan.login.domain.repository.UserSignRepository;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import com.growplan.user.dto.request.SignUpRequest;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.SignUpResponse;
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
    private final UserSignRepository userSignRepository;

    public UserResponse getUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        return UserResponse.of(user);
    }

    public UserListResponse getUsers() {
        final List<User> users = userRepository.findAll();

        return UserListResponse.of(users);
    }

    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
        final UserSign userSign = new UserSign(
                signUpRequest.getUsername(),
                signUpRequest.getPassword()
        );

        final UserSign savedUserSign = userSignRepository.save(userSign);

        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getBirthdate(),
                signUpRequest.getEmail(),
                signUpRequest.getNumber(),
                savedUserSign
        );

        final User savedUser = userRepository.save(user);

        return SignUpResponse.of(savedUser);
    }

    public void updateUser(final Long userId, final UserUpdateRequest userUpdateRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        user.updateUser(
                userUpdateRequest.getName(),
                userUpdateRequest.getBirthdate(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getNumber()
        );

        userRepository.save(user);
    }

    public void deleteAccount(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        userRepository.deleteById(user.getId());
    }
}
