package com.example.codebogo.quiz.repository;

import com.example.codebogo.quiz.entity.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
