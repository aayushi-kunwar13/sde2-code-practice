package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProblemRepository implements ProblemRepository {

    private final Map<String, Problem> problemsById = new LinkedHashMap<>();

    @Override
    public void save(Problem problem) {
        problemsById.put(problem.getId(), problem);
    }

    @Override
    public Optional<Problem> findById(String problemId) {
        return Optional.ofNullable(problemsById.get(problemId));
    }

    @Override
    public List<Problem> findAll() {
        return new ArrayList<>(problemsById.values());
    }
}
