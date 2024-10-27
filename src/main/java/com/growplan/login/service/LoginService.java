package com.growplan.login.service;

import com.growplan.common.exception.AuthException;
import com.growplan.common.exception.BadRequestException;
import com.growplan.login.domain.RefreshToken;
import com.growplan.login.domain.UserSign;
import com.growplan.login.domain.repository.RefreshTokenRepository;
import com.growplan.login.domain.repository.UserSignRepository;
import com.growplan.login.dto.request.LoginRequest;
import com.growplan.login.dto.request.SignUpRequest;
import com.growplan.login.dto.response.LoginResponse;
import com.growplan.login.dto.response.LoginStatusResponse;
import com.growplan.login.jwt.JwtExtractor;
import com.growplan.login.jwt.JwtProvider;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.growplan.common.code.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserSignRepository userSignRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;

    public LoginStatusResponse getLoginStatus() {
        final String accessToken = jwtExtractor.getAccessToken();
        final String refreshToken = jwtExtractor.getRefreshToken();

        if (jwtProvider.isValidRefreshAndInvalidAccess(refreshToken, accessToken)) {
            final RefreshToken userRefreshToken = refreshTokenRepository.findById(refreshToken)
                    .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));
            final String newAccessToken = jwtProvider.generateAccessToken(userRefreshToken.getUserId().toString());
            return LoginStatusResponse.of(true, newAccessToken);
        }

        if (jwtProvider.isValidRefreshAndValidAccess(refreshToken, accessToken)) {
            return LoginStatusResponse.of(true, accessToken);
        }

        return LoginStatusResponse.of(false, null);
    }

    public LoginResponse login(final LoginRequest loginRequest) {
        final UserSign userSign = userSignRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USERNAME));

        // TODO password 암호화 과정 추가
        if (!userSign.getPassword().equals(loginRequest.getPassword())) {
            throw new BadRequestException(INCORRECT_PASSWORD);
        }

        final String accessToken = jwtProvider.generateAccessToken(userSign.getUser().getId().toString());
        final String refreshToken = jwtProvider.generateRefreshToken();

        return LoginResponse.of(accessToken, refreshToken);
    }
}
