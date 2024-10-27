package com.growplan.user.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.login.domain.RefreshToken;
import com.growplan.login.domain.repository.RefreshTokenRepository;
import com.growplan.login.dto.request.SignUpRequest;
import com.growplan.login.dto.response.LoginResponse;
import com.growplan.login.jwt.JwtExtractor;
import com.growplan.login.jwt.JwtProvider;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
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

    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponse getUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER));

        return UserResponse.of(user);
    }

    public UserListResponse getUsers() {
        final List<User> users = userRepository.findAll();

        return UserListResponse.of(users);
    }

    public LoginResponse signUp(final SignUpRequest signUpRequest) {
        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getBirthdate(),
                signUpRequest.getEmail(),
                signUpRequest.getNumber()
        );

        final User savedUser = userRepository.save(user);

        final String accessToken = jwtProvider.generateAccessToken(savedUser.getId().toString());

        final RefreshToken refreshToken = new RefreshToken(jwtProvider.generateRefreshToken(), savedUser.getId());
        refreshTokenRepository.save(refreshToken);

        return LoginResponse.of(accessToken, refreshToken.getToken());
    }

    public void deleteAccount(final Long userId) {
        final String refreshToken = jwtExtractor.getRefreshToken();

        refreshTokenRepository.deleteById(refreshToken);
        userRepository.deleteById(userId);
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
}
