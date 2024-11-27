package com.growplan.user.controller;

import com.growplan.user.dto.request.SignUpRequest;
import com.growplan.user.dto.response.SignUpResponse;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.UserListResponse;
import com.growplan.user.dto.response.UserResponse;
import com.growplan.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User-Controller", description = "유저 API 엔드포인트")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 전체 조회", description = "유저를 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "유저 전체 조회에 성공했습니다.")
    @GetMapping
    public ResponseEntity<UserListResponse> getUsers() {
        final UserListResponse userListResponse = userService.getUsers();
        return ResponseEntity.ok().body(userListResponse);
    }

    @Operation(summary = "추가 정보 저장", description = "추가 정보를 저장합니다.")
    @ApiResponse(responseCode = "200", description = "추가 정보 저장에 성공했습니다.")
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        final SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        return ResponseEntity.ok().body(signUpResponse);
    }

    @Operation(summary = "유저 단일 조회", description = "유저를 단일 조회합니다.")
    @ApiResponse(responseCode = "200", description = "유저 단일 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") final Long userId) {
        final UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "유저 수정", description = "유저를 수정합니다.")
    @ApiResponse(responseCode = "204", description = "유저 수정에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") final Long userId,
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저 삭제", description = "유저를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "유저 삭제에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("userId") final Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
}
