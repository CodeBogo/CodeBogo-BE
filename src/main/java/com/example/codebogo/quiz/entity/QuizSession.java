package com.example.codebogo.quiz.entity;

import com.example.codebogo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_sessions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer currentIndex;

    @Column(nullable = false)
    private Integer correctCount;

    @Column(nullable = false)
    private Integer earnedPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuizStatus status;

    @CreationTimestamp
    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    public void addCorrectCount() {
        this.correctCount += 1;
    }

    public void addEarnedPoint(int point) {
        this.earnedPoint += point;
    }

    public void nextQuestion() {
        this.currentIndex += 1;
    }

    public void finish() {
        this.status = QuizStatus.FINISHED;
        this.finishedAt = java.time.LocalDateTime.now();
    }
}
