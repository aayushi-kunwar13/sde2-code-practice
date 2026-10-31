package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Submission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemorySubmissionRepository implements SubmissionRepository {

    private final List<Submission> submissions = new ArrayList<>();

    @Override
    public void save(Submission submission) {
        submissions.add(submission);
    }

    @Override
    public List<Submission> findByContestant(String contestantId) {
        return submissions.stream()
                .filter(submission -> submission.getContestantId().equals(contestantId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Submission> findByProblem(String problemId) {
        return submissions.stream()
                .filter(submission -> submission.getProblemId().equals(problemId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Submission> findAll() {
        return new ArrayList<>(submissions);
    }
}
