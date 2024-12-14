package com.growplan.login.service;

import com.growplan.login.domain.OauthMember;
import com.growplan.login.dto.response.KakaoMemberResponse;
import com.growplan.login.dto.response.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.growplan.login.domain.type.SocialLoginType.KAKAO;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {

    private final LoginApiClient loginApiClient;

    @Value("${spring.oauth2.kakao.client-id}")
    private String clientId;

    @Value("${spring.oauth2.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.oauth2.kakao.redirect-uri}")
    private String redirectUri;

    public OauthMember login(final String code) {
        final KakaoTokenResponse kakaoTokenResponse = loginApiClient.getKakaoToken(tokenRequestParams(code));
        final KakaoMemberResponse kakaoMemberResponse = loginApiClient.getKakaoMemberInfo("Bearer " + kakaoTokenResponse.getAccessToken());
        final String socialLoginId = kakaoMemberResponse.getId().toString();

        return new OauthMember(
                kakaoMemberResponse.getKakaoAccount().getEmail(),
                kakaoMemberResponse.getKakaoAccount().getProfile().getNickname(),
                socialLoginId,
                KAKAO
        );
    }

    private MultiValueMap<String, String> tokenRequestParams(final String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", redirectUri);
        params.add("client_id", clientId);
        params.add("code", code);
        params.add("client_secret", clientSecret);
        return params;
    }
}