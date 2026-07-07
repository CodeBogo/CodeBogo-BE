package com.example.codebogo.history.entity;

import com.example.codebogo.problem.entity.AnswerOption;
import com.example.codebogo.problem.entity.Problem;
import com.example.codebogo.quiz.entity.QuizSession;
import com.example.codebogo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "problem_history",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"quiz_session_id", "problem_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProblemHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_session_id", nullable = false)
    private QuizSession quizSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "selected_answer", nullable = false, length = 1)
    private String selectedAnswer;

    @Column(nullable = false)
    private Boolean correct;

    @Column(name = "earned_point", nullable = false)
    private Integer earnedPoint;

    @CreationTimestamp
    @Column(name = "solved_at", nullable = false, updatable = false)
    private LocalDateTime solvedAt;
}
