package com.example.codebogo.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserStatusResponse {
    private Long userId;
    private String nickname;
    private Integer point;
    private Integer streak;
    private LocalDate lastSolvedDate;
}
