package com.growplan.login.controller;

import com.growplan.login.dto.request.LoginRequest;
import com.growplan.login.dto.response.LoginResponse;
import com.growplan.login.dto.response.LoginStatusResponse;
import com.growplan.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login-Controller", description = "로그인 API 엔드포인트")
@RequestMapping("/sign")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인 여부 확인", description = "로그인 여부를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 여부 확인에 성공했습니다.")
    @GetMapping
    public ResponseEntity<LoginStatusResponse> getLoginStatus() {
        final LoginStatusResponse loginStatusResponse = loginService.getLoginStatus();
        return ResponseEntity.ok().body(loginStatusResponse);
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @ApiResponse(responseCode = "200", description = "로그인에 성공했습니다.")
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final LoginResponse loginResponse = loginService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
