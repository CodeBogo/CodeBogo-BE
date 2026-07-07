package com.example.codebogo.problem.repository;

import com.example.codebogo.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = "select * from problems order by rand() limit :count", nativeQuery = true)
    List<Problem> findRandomProblems(@Param("count") int count);
}
