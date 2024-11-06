package com.growplan.child.controller;

import com.growplan.child.dto.request.ChildRequest;
import com.growplan.child.dto.response.ChildListResponse;
import com.growplan.child.dto.response.ChildResponse;
import com.growplan.child.service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/children")
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public ResponseEntity<ChildListResponse> saveChild(@PathVariable("userId") final Long userId) {
        final ChildListResponse childListResponse = childService.getChildren(userId);
        return ResponseEntity.ok().body(childListResponse);
    }

    @PostMapping
    public ResponseEntity<Void> saveChild(
            @PathVariable("userId") final Long userId,
            @RequestBody @Valid final ChildRequest childRequest
    ) {
        childService.saveChild(userId, childRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{childId}")
    public ResponseEntity<ChildResponse> getChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final ChildResponse childResponse = childService.getChild(userId, childId);
        return ResponseEntity.ok().body(childResponse);
    }

    @PutMapping("/{childId}")
    public ResponseEntity<Void> updateChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestBody @Valid final ChildRequest childRequest
    ) {
        childService.updateChild(userId, childId, childRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> deleteChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        childService.deleteChild(userId, childId);
        return ResponseEntity.noContent().build();
    }
}
