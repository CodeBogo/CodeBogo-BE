package com.example.codebogo.quiz.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitAnswerRequest {

    @NotBlank(message = "선택한 답안은 필수입니다.")
    private String selectedAnswer;
}
