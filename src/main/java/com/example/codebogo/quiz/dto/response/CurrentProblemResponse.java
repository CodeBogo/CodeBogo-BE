package com.example.codebogo.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class CurrentProblemResponse {

    private Long sessionId;
    private int current;
    private int total;

    private Long problemId;
    private String language;
    private String title;
    private String code;
    private Map<String, String> options;
    private int point;
}
