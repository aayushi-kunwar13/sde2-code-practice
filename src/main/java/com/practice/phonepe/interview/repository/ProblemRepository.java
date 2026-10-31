package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {

    void save(Problem problem);

    Optional<Problem> findById(String problemId);

    List<Problem> findAll();
}
