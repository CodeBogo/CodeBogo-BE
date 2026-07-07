package com.example.codebogo.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmitAnswerResponse {

    private boolean correct;
    private String message;
    private String feedback;
    private String correctAnswer;
    private String correctAnswerText;
    private String correctCode;
    private int earnedPoint;
    private int currentPoint;
    private int currentStreak;
    private int current;
    private int total;
    private boolean hasNext;
}
