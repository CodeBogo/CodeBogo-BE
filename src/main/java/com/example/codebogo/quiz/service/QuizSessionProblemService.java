package com.example.codebogo.quiz.service;

import com.example.codebogo.history.entity.ProblemHistory;
import com.example.codebogo.history.repository.ProblemHistoryRepository;
import com.example.codebogo.problem.entity.Problem;
import com.example.codebogo.quiz.dto.request.SubmitAnswerRequest;
import com.example.codebogo.quiz.dto.response.CurrentProblemResponse;
import com.example.codebogo.quiz.dto.response.NextQuestionResponse;
import com.example.codebogo.quiz.dto.response.SubmitAnswerResponse;
import com.example.codebogo.quiz.entity.QuizSession;
import com.example.codebogo.quiz.entity.QuizSessionProblem;
import com.example.codebogo.quiz.entity.QuizStatus;
import com.example.codebogo.quiz.repository.QuizSessionProblemRepository;
import com.example.codebogo.quiz.repository.QuizSessionRepository;
import com.example.codebogo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizSessionProblemService {

    private final QuizSessionRepository quizSessionRepository;
    private final QuizSessionProblemRepository quizSessionProblemRepository;
    private final ProblemHistoryRepository problemHistoryRepository;

    @Transactional(readOnly = true)
    public CurrentProblemResponse getCurrentProblem(Long sessionId) {
        QuizSession quizSession = getQuizSession(sessionId);

        QuizSessionProblem currentQuizSessionProblem = getCurrentQuizSessionProblem(quizSession);
        Problem problem = currentQuizSessionProblem.getProblem();

        return CurrentProblemResponse.builder()
                .sessionId(quizSession.getId())
                .current(quizSession.getCurrentIndex())
                .total(quizSession.getTotalCount())
                .problemId(problem.getId())
                .language(problem.getLanguage())
                .title(problem.getTitle())
                .code(problem.getCode())
                .options(createOptions(problem))
                .point(problem.getPoint())
                .build();
    }

    public SubmitAnswerResponse submitAnswer(Long sessionId, Long problemId, SubmitAnswerRequest request) {
        QuizSession quizSession = getQuizSession(sessionId);

        if (quizSession.getStatus() == QuizStatus.FINISHED) {
            throw new RuntimeException("이미 종료된 퀴즈입니다.");
        }

        QuizSessionProblem currentQuizSessionProblem = getCurrentQuizSessionProblem(quizSession);
        Problem problem = currentQuizSessionProblem.getProblem();

        if (!problem.getId().equals(problemId)) {
            throw new RuntimeException("현재 제출 가능한 문제가 아닙니다.");
        }

        if (problemHistoryRepository.existsByQuizSessionIdAndProblemId(sessionId, problemId)) {
            throw new RuntimeException("이미 제출한 문제입니다.");
        }

        String selectedAnswer = normalizeAnswer(request.getSelectedAnswer());
        String correctAnswer = problem.getAnswer();

        boolean correct = correctAnswer.equals(selectedAnswer);
        int earnedPoint = correct ? problem.getPoint() : 0;

        User user = quizSession.getUser();

        if (correct) {
            quizSession.addCorrectCount();
            quizSession.addEarnedPoint(earnedPoint);
            user.addPoint(earnedPoint);
        }

        ProblemHistory history = ProblemHistory.builder()
                .user(user)
                .quizSession(quizSession)
                .problem(problem)
                .selectedAnswer(selectedAnswer)
                .correct(correct)
                .earnedPoint(earnedPoint)
                .build();

        problemHistoryRepository.save(history);

        boolean isLastProblem = quizSession.getCurrentIndex() >= quizSession.getTotalCount();

// 마지막 문제를 제출한 순간 streak 반영 (하루 1회만)
        if (isLastProblem) {
            System.out.println("마지막 문제 제출 감지");
            user.completeQuizToday(LocalDate.now());
            System.out.println("streak 반영 후 = " + user.getStreak());
            quizSession.finish();
        }


        return SubmitAnswerResponse.builder()
                .correct(correct)
                .message(correct ? "정답입니다!" : "오답입니다.")
                .feedback(problem.getFeedback())
                .correctAnswer(correctAnswer)
                .correctAnswerText(problem.getAnswerText())
                .correctCode(problem.getCorrectCode())
                .earnedPoint(earnedPoint)
                .currentPoint(user.getPoint())
                .currentStreak(user.getStreak())
                .current(quizSession.getCurrentIndex())
                .total(quizSession.getTotalCount())
                .hasNext(!isLastProblem)
                .build();
    }

    public NextQuestionResponse moveToNextQuestion(Long sessionId) {
        QuizSession quizSession = getQuizSession(sessionId);

        if (quizSession.getStatus() == QuizStatus.FINISHED) {
            return NextQuestionResponse.builder()
                    .sessionId(quizSession.getId())
                    .current(quizSession.getCurrentIndex())
                    .total(quizSession.getTotalCount())
                    .hasNext(false)
                    .finished(true)
                    .build();
        }

        QuizSessionProblem currentQuizSessionProblem = getCurrentQuizSessionProblem(quizSession);
        Long currentProblemId = currentQuizSessionProblem.getProblem().getId();

        boolean submitted = problemHistoryRepository.existsByQuizSessionIdAndProblemId(
                quizSession.getId(),
                currentProblemId
        );

        if (!submitted) {
            throw new RuntimeException("현재 문제를 먼저 제출해야 다음 문제로 이동할 수 있습니다.");
        }

        if (quizSession.getCurrentIndex() >= quizSession.getTotalCount()) {
            quizSession.finish();

            return NextQuestionResponse.builder()
                    .sessionId(quizSession.getId())
                    .current(quizSession.getCurrentIndex())
                    .total(quizSession.getTotalCount())
                    .hasNext(false)
                    .finished(true)
                    .build();
        }

        quizSession.nextQuestion();

        return NextQuestionResponse.builder()
                .sessionId(quizSession.getId())
                .current(quizSession.getCurrentIndex())
                .total(quizSession.getTotalCount())
                .hasNext(true)
                .finished(false)
                .build();
    }

    private QuizSession getQuizSession(Long sessionId) {
        return quizSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("퀴즈 세션을 찾을 수 없습니다. sessionId=" + sessionId));
    }

    private QuizSessionProblem getCurrentQuizSessionProblem(QuizSession quizSession) {
        return quizSessionProblemRepository.findByQuizSessionIdAndOrderNo(
                        quizSession.getId(),
                        quizSession.getCurrentIndex()
                )
                .orElseThrow(() -> new RuntimeException("현재 문제를 찾을 수 없습니다."));
    }

    private Map<String, String> createOptions(Problem problem) {
        Map<String, String> options = new LinkedHashMap<>();
        options.put("A", problem.getOptionA());
        options.put("B", problem.getOptionB());
        options.put("C", problem.getOptionC());
        options.put("D", problem.getOptionD());
        return options;
    }

    private String normalizeAnswer(String selectedAnswer) {
        if (selectedAnswer == null || selectedAnswer.isBlank()) {
            throw new RuntimeException("selectedAnswer 값은 필수입니다.");
        }

        String normalized = selectedAnswer.trim().toUpperCase(Locale.ROOT);

        if (!normalized.equals("A")
                && !normalized.equals("B")
                && !normalized.equals("C")
                && !normalized.equals("D")) {
            throw new RuntimeException("selectedAnswer 는 A, B, C, D 중 하나여야 합니다.");
        }

        return normalized;
    }
}
