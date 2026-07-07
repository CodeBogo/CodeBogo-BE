package com.example.codebogo.quiz.controller;


import com.example.codebogo.quiz.dto.request.SubmitAnswerRequest;
import com.example.codebogo.quiz.dto.response.*;
import com.example.codebogo.quiz.service.QuizSessionProblemService;
import com.example.codebogo.quiz.service.QuizSessionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-sessions")
@RequiredArgsConstructor
public class QuizController {

    private final QuizSessionService quizSessionService;
    private final QuizSessionProblemService quizSessionProblemService;

    @Operation(summary = "문제 시작", description = "문제를 시작합니다.")
    @PostMapping("/start")
    public ResponseEntity<StartQuizResponse> startQuiz() {
        StartQuizResponse response = quizSessionService.startQuiz();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "현재 문제 확인", description = "현재 문제를 확인합니다.")
    @GetMapping("/{sessionId}/current")
    public ResponseEntity<CurrentProblemResponse> getCurrentProblem(@PathVariable Long sessionId) {
        return ResponseEntity.ok(quizSessionProblemService.getCurrentProblem(sessionId));
    }

    @Operation(summary = "답안 제출", description = "다음 문제로 이동하기 위해 답안을 제출해주세요.")
    @PostMapping("/{sessionId}/problems/{problemId}/submit")
    public ResponseEntity<SubmitAnswerResponse> submitAnswer(
            @PathVariable Long sessionId,
            @PathVariable Long problemId,
            @RequestBody SubmitAnswerRequest request
    ) {
        return ResponseEntity.ok(
                quizSessionProblemService.submitAnswer(sessionId, problemId, request)
        );
    }

    @Operation(summary = "다음 문제 이동", description = "다음 문제로 이동하거나 퀴즈를 종료합니다.")
    @PostMapping("/{sessionId}/next")
    public ResponseEntity<NextQuestionResponse> moveToNextQuestion(@PathVariable Long sessionId) {
        NextQuestionResponse response = quizSessionProblemService.moveToNextQuestion(sessionId);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "결과확인", description = "문제 다 풀고 결과를 확인합니다.")
    @GetMapping("/{sessionId}/result")
    public ResponseEntity<QuizResultResponse> getResult(@PathVariable Long sessionId) {
        return ResponseEntity.ok(quizSessionService.getQuizResult(sessionId));
    }
}
