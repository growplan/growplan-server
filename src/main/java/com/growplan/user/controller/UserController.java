package com.growplan.user.controller;

import com.growplan.user.dto.request.SignUpRequest;
import com.growplan.user.dto.response.SignUpResponse;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.UserListResponse;
import com.growplan.user.dto.response.UserResponse;
import com.growplan.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User-Controller", description = "사용자 API 엔드포인트")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserListResponse> getUsers() {
        final UserListResponse userListResponse = userService.getUsers();
        return ResponseEntity.ok().body(userListResponse);
    }

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        final SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        return ResponseEntity.ok().body(signUpResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") final Long userId) {
        final UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok().body(userResponse);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") final Long userId,
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("userId") final Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
}
