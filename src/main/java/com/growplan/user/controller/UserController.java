package com.growplan.user.controller;

import com.growplan.child.dto.request.ChildRequest;
import com.growplan.child.service.ChildService;
import com.growplan.login.domain.Accessor;
import com.growplan.login.domain.Auth;
import com.growplan.login.dto.request.SignUpRequest;
import com.growplan.login.dto.response.LoginResponse;
import com.growplan.user.dto.request.UserUpdateRequest;
import com.growplan.user.dto.response.UserListResponse;
import com.growplan.user.dto.response.UserResponse;
import com.growplan.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ChildService childService;

    @GetMapping
    public ResponseEntity<UserListResponse> getUsers() {
        final UserListResponse userListResponse = userService.getUsers();
        return ResponseEntity.ok().body(userListResponse);
    }

    @PostMapping
    public ResponseEntity<LoginResponse> signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        final LoginResponse loginResponse = userService.signUp(signUpRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@Auth final Accessor accessor) {
        userService.deleteAccount(accessor.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@Auth final Accessor accessor) {
        final UserResponse userResponse = userService.getUser(accessor.getUserId());
        return ResponseEntity.ok().body(userResponse);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @Auth final Accessor accessor,
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userService.updateUser(accessor.getUserId(), userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/children")
    public ResponseEntity<Void> saveChild(
            @Auth final Accessor accessor,
            @RequestBody @Valid final ChildRequest childRequest
    ) {
        childService.saveChild(accessor.getUserId(), childRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/children/{childId}")
    public ResponseEntity<Void> getChild(
            @Auth final Accessor accessor,
            @PathVariable("childId") final Long childId
    ) {
        childService.getChild(accessor.getUserId(), childId);
        return ResponseEntity.noContent().build();
    }
}
