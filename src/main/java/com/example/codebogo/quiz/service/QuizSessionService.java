package com.example.codebogo.quiz.service;

import com.example.codebogo.history.entity.ProblemHistory;
import com.example.codebogo.history.repository.ProblemHistoryRepository;
import com.example.codebogo.problem.entity.Problem;
import com.example.codebogo.problem.repository.ProblemRepository;
import com.example.codebogo.quiz.dto.response.QuizResultResponse;
import com.example.codebogo.quiz.dto.response.StartQuizResponse;
import com.example.codebogo.quiz.entity.QuizSession;
import com.example.codebogo.quiz.entity.QuizSessionProblem;
import com.example.codebogo.quiz.entity.QuizStatus;
import com.example.codebogo.quiz.repository.QuizSessionProblemRepository;
import com.example.codebogo.quiz.repository.QuizSessionRepository;
import com.example.codebogo.user.entity.User;
import com.example.codebogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizSessionService {

    private static final String DEMO_NICKNAME = "카리나";
    private static final int QUIZ_SIZE = 5;

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizSessionProblemRepository quizSessionProblemRepository;
    private final ProblemHistoryRepository problemHistoryRepository;

    public StartQuizResponse startQuiz() {
        System.out.println("1. startQuiz 진입");

        User user = userRepository.findByNickname(DEMO_NICKNAME)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        System.out.println("2. user 조회 성공: " + user.getNickname());

        List<Problem> problems = problemRepository.findRandomProblems(QUIZ_SIZE);
        System.out.println("3. 문제 조회 완료, 개수: " + problems.size());

        if (problems.isEmpty()) {
            throw new RuntimeException("문제가 한 개도 조회되지 않았습니다. problems 테이블 데이터를 확인하세요.");
        }

        if (problems.size() < QUIZ_SIZE) {
            throw new RuntimeException("문제가 5개 미만이라 퀴즈를 시작할 수 없습니다. 현재 개수: " + problems.size());
        }

        QuizSession quizSession = QuizSession.builder()
                .user(user)
                .totalCount(QUIZ_SIZE)
                .currentIndex(1)
                .correctCount(0)
                .earnedPoint(0)
                .status(QuizStatus.IN_PROGRESS)
                .build();

        quizSessionRepository.save(quizSession);
        System.out.println("4. quizSession 저장 완료: " + quizSession.getId());

        for (int i = 0; i < problems.size(); i++) {
            QuizSessionProblem quizSessionProblem = QuizSessionProblem.builder()
                    .quizSession(quizSession)
                    .problem(problems.get(i))
                    .orderNo(i + 1)
                    .build();

            quizSessionProblemRepository.save(quizSessionProblem);
        }
        System.out.println("5. quizSessionProblem 저장 완료");

        Problem firstProblem = problems.get(0);
        System.out.println("6. 첫 문제 선택 완료: " + firstProblem.getTitle());

        Map<String, String> options = new LinkedHashMap<>();
        options.put("A", firstProblem.getOptionA());
        options.put("B", firstProblem.getOptionB());
        options.put("C", firstProblem.getOptionC());
        options.put("D", firstProblem.getOptionD());
        System.out.println("7. 보기 맵 생성 완료");

        StartQuizResponse response = StartQuizResponse.builder()
                .sessionId(quizSession.getId())
                .current(quizSession.getCurrentIndex())
                .total(quizSession.getTotalCount())
                .problemId(firstProblem.getId())
                .language(firstProblem.getLanguage())
                .title(firstProblem.getTitle())
                .code(firstProblem.getCode())
                .options(options)
                .point(firstProblem.getPoint())
                .build();

        System.out.println("8. StartQuizResponse 생성 완료: sessionId=" + response.getSessionId());
        return response;
    }

    @Transactional(readOnly = true)
    public QuizResultResponse getQuizResult(Long sessionId) {
        System.out.println("9. getQuizResult 진입: sessionId=" + sessionId);

        QuizSession quizSession = getSession(sessionId);
        User user = quizSession.getUser();

        int totalCount = quizSession.getTotalCount();
        int correctCount = quizSession.getCorrectCount();
        int wrongCount = totalCount - correctCount;
        int scorePercent = totalCount == 0 ? 0 : (correctCount * 100) / totalCount;

        // 사용자가 틀린 문제 기록만 조회
        List<ProblemHistory> wrongHistories =
                problemHistoryRepository.findByQuizSessionIdAndCorrectFalse(sessionId);

        // 틀린 문제의 topic만 추출, 중복 제거
        List<String> wrongTopics = wrongHistories.stream()
                .map(history -> history.getProblem().getTopic())
                .distinct()
                .toList();

        QuizResultResponse response = QuizResultResponse.builder()
                .sessionId(quizSession.getId())
                .totalCount(totalCount)
                .correctCount(correctCount)
                .wrongCount(wrongCount)
                .scorePercent(scorePercent)
                .earnedPoint(quizSession.getEarnedPoint())
                .currentStreak(user.getStreak())
                .wrongTopics(wrongTopics)
                .build();

        System.out.println("10. QuizResultResponse 생성 완료: sessionId=" + response.getSessionId());
        return response;
    }

    @Transactional(readOnly = true)
    public QuizSession getSession(Long sessionId) {
        return quizSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("퀴즈 세션을 찾을 수 없습니다. sessionId=" + sessionId));
    }
}
