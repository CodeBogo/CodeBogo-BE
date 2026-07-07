package com.example.codebogo.history.repository;

import com.example.codebogo.history.entity.ProblemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemHistoryRepository extends JpaRepository<ProblemHistory, Long> {

    boolean existsByQuizSessionIdAndProblemId(Long quizSessionId, Long problemId);

    List<ProblemHistory> findByQuizSessionIdAndCorrectFalse(Long quizSessionId);
}
