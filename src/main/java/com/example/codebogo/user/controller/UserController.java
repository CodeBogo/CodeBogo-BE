package com.example.codebogo.user.controller;

import com.example.codebogo.user.dto.response.UserStatusResponse;
import com.example.codebogo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회합니다.")
    @GetMapping("/status")
    public ResponseEntity<UserStatusResponse> getUserStatus() {
        return ResponseEntity.ok(userService.getUserStatus());
    }
}
