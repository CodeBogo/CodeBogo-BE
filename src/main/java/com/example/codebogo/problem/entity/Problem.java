package com.example.codebogo.problem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String language;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(name = "option_a", nullable = false, columnDefinition = "TEXT")
    private String optionA;

    @Column(name = "option_b", nullable = false, columnDefinition = "TEXT")
    private String optionB;

    @Column(name = "option_c", nullable = false, columnDefinition = "TEXT")
    private String optionC;

    @Column(name = "option_d", nullable = false, columnDefinition = "TEXT")
    private String optionD;

    @Column(name = "answer", nullable = false, length = 1)
    private String answer;


    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @Lob
    @Column(name = "correct_code", nullable = false, columnDefinition = "TEXT")
    private String correctCode;

    @Column(nullable = false)
    private Integer point;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "topic", nullable = false, length = 100)
    private String topic;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isCorrect(String selectedAnswer) {
        return this.answer.equals(selectedAnswer);
    }

    public String getAnswerText() {
        return switch (answer) {
            case "A" -> optionA;
            case "B" -> optionB;
            case "C" -> optionC;
            case "D" -> optionD;
            default -> throw new IllegalArgumentException("올바르지 않은 answer 값: " + answer);
        };
    }
}
