package com.growplan.login.service;

import com.growplan.login.domain.OauthMember;
import com.growplan.login.dto.response.NaverMemberResponse;
import com.growplan.login.dto.response.NaverTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.growplan.login.domain.type.SocialLoginType.NAVER;

@Service
@RequiredArgsConstructor
@Transactional
public class NaverLoginService {

    private final LoginApiClient loginApiClient;

    @Value("${spring.oauth2.naver.client-id}")
    private String clientId;

    @Value("${spring.oauth2.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.oauth2.naver.redirect-uri}")
    private String redirectUri;

    public OauthMember login(final String code) {
        final NaverTokenResponse naverTokenResponse = loginApiClient.getNaverToken(tokenRequestParams(code));
        final NaverMemberResponse naverMemberResponse = loginApiClient.getNaverMemberInfo("Bearer " + naverTokenResponse.getAccessToken());
        final String socialLoginId = naverMemberResponse.getNaverMemberDetail().getId();
        final NaverMemberResponse.NaverMemberDetail memberDetail = naverMemberResponse.getNaverMemberDetail();

        return new OauthMember(
                memberDetail.getEmail(),
                socialLoginId,
                NAVER
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
