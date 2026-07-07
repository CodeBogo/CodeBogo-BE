package com.example.codebogo.quiz.repository;

import com.example.codebogo.quiz.entity.QuizSessionProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizSessionProblemRepository extends JpaRepository<QuizSessionProblem, Long> {

    Optional<QuizSessionProblem> findByQuizSessionIdAndOrderNo(Long quizSessionId, Integer orderNo);

    Optional<QuizSessionProblem> findByQuizSessionIdAndProblemId(Long quizSessionId, Long problemId);
}
