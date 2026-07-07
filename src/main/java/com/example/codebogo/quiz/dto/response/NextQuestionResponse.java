package com.example.codebogo.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NextQuestionResponse {

    private Long sessionId;
    private int current;
    private int total;
    private boolean hasNext;
    private boolean finished;
}
