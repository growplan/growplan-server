package com.growplan.login.service;

import com.growplan.common.exception.AuthException;
import com.growplan.common.exception.BadRequestException;
import com.growplan.login.domain.MemberInfo;
import com.growplan.login.domain.OauthMember;
import com.growplan.login.domain.RefreshToken;
import com.growplan.login.domain.repository.RefreshTokenRepository;
import com.growplan.login.dto.response.LoginResponse;
import com.growplan.login.dto.response.LoginStatusResponse;
import com.growplan.login.jwt.JwtExtractor;
import com.growplan.login.jwt.JwtProvider;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.growplan.common.code.ExceptionCode.FAIL_TO_SOCIAL_LOGIN;
import static com.growplan.common.code.ExceptionCode.INVALID_REFRESH_TOKEN;
import static com.growplan.login.domain.type.SocialLoginType.KAKAO;
import static com.growplan.login.domain.type.SocialLoginType.NAVER;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;
    private final UserRepository userRepository;
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;

    public LoginResponse login(final String provider, final String code) {
        if (provider.equals(KAKAO.getCode())) {
            return saveMember(kakaoLoginService.login(code));
        } else if (provider.equals(NAVER.getCode())) {
            return saveMember(naverLoginService.login(code));
        }
        throw new BadRequestException(FAIL_TO_SOCIAL_LOGIN);
    }

    private MemberInfo findOrCreateMember(final OauthMember oauthMember) {
        final User user = userRepository.findBySocialLoginId(oauthMember.getSocialLoginId())
                .orElseGet(() -> createMember(oauthMember));

        return new MemberInfo(user, false);
    }

    private User createMember(final OauthMember oauthMember) {
        final User user = new User(
                oauthMember.getEmail(),
                oauthMember.getNickname(),
                oauthMember.getSocialLoginId(),
                oauthMember.getSocialLoginType()
        );

        return userRepository.save(user);
    }

    private LoginResponse saveMember(final OauthMember oauthMember) {
        final MemberInfo memberInfo = findOrCreateMember(oauthMember);

        return LoginResponse.of(
                memberInfo.getUser(),
                memberInfo.getIsAdditionalSignup(),
                null,
                null
        );
    }

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
}
