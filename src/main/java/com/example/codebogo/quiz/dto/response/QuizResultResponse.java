package com.example.codebogo.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizResultResponse {

    private Long sessionId;
    private int totalCount;
    private int correctCount;
    private int wrongCount;
    private int scorePercent;
    private int earnedPoint;
    private int currentStreak;
    private List<String> wrongTopics;
}
