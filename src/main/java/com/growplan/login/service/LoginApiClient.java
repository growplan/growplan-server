package com.growplan.login.service;

import com.growplan.login.dto.response.KakaoMemberResponse;
import com.growplan.login.dto.response.KakaoTokenResponse;
import com.growplan.login.dto.response.NaverMemberResponse;
import com.growplan.login.dto.response.NaverTokenResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public interface LoginApiClient {

    @PostExchange("https://kauth.kakao.com/oauth/token")
    KakaoTokenResponse getKakaoToken(@RequestParam final MultiValueMap<String, String> params);

    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponse getKakaoMemberInfo(@RequestHeader(name = AUTHORIZATION) final String bearerToken);

    @PostExchange("https://nid.naver.com/oauth2.0/token")
    NaverTokenResponse getNaverToken(@RequestParam final MultiValueMap<String, String> params);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponse getNaverMemberInfo(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
