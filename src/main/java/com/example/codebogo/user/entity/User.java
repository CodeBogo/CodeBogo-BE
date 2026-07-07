package com.example.codebogo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer streak;

    @Column(name = "last_solved_date")
    private LocalDate lastSolvedDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addPoint(int point) {
        this.point += point;
    }

    // 하루에 한 번만 streak 증가
    public void completeQuizToday(LocalDate today) {
        System.out.println("before streak = " + this.streak + ", lastSolvedDate = " + this.lastSolvedDate);

        if (this.streak == null || this.streak < 0) {
            this.streak = 0;
        }

        if (this.lastSolvedDate == null) {
            this.streak = 1;
            this.lastSolvedDate = today;
            System.out.println("after streak = " + this.streak + ", lastSolvedDate = " + this.lastSolvedDate);
            return;
        }

        if (this.lastSolvedDate.equals(today)) {
            if (this.streak == 0) {
                this.streak = 1;
            }
            System.out.println("after streak = " + this.streak + ", lastSolvedDate = " + this.lastSolvedDate);
            return;
        }

        if (this.lastSolvedDate.plusDays(1).equals(today)) {
            this.streak += 1;
        } else {
            this.streak = 1;
        }

        this.lastSolvedDate = today;
        System.out.println("after streak = " + this.streak + ", lastSolvedDate = " + this.lastSolvedDate);
    }

}
