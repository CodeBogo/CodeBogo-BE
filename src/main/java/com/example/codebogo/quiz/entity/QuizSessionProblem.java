package com.example.codebogo.quiz.entity;

import com.example.codebogo.problem.entity.Problem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "quiz_session_problems",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"quiz_session_id", "order_no"}),
                @UniqueConstraint(columnNames = {"quiz_session_id", "problem_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QuizSessionProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_session_id", nullable = false)
    private QuizSession quizSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "order_no", nullable = false)
    private Integer orderNo;
}
