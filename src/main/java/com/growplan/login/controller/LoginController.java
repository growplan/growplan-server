package com.growplan.login.controller;

import com.growplan.login.dto.response.LoginResponse;
import com.growplan.login.dto.response.LoginStatusResponse;
import com.growplan.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login-Controller", description = "로그인 API 엔드포인트")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "소셜 로그인", description = "소셜 로그인을 합니다.")
    @ApiResponse(responseCode = "200", description = "소셜 로그인에 성공했습니다.")
    @Parameter(name = "provider", description = "소셜 로그인 제공자 (kakao, naver)", required = true, example = "kakao")
    @Parameter(name = "code", description = "소셜 로그인 인가 코드", required = true, example = "C2tSMdxIQo...")
    @PostMapping("/auth/login/{provider}")
    public ResponseEntity<LoginResponse> login(
            @PathVariable("provider") final String provider,
            @RequestParam("code") final String code
    ) {
        final LoginResponse loginResponse = loginService.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "로그인 여부 확인", description = "로그인 여부를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 여부 확인에 성공했습니다.")
    @GetMapping("/login")
    public ResponseEntity<LoginStatusResponse> getLoginStatus() {
        final LoginStatusResponse loginStatusResponse = loginService.getLoginStatus();
        return ResponseEntity.ok().body(loginStatusResponse);
    }
}
