package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Submission;

import java.util.List;

public interface SubmissionRepository {

    void save(Submission submission);

    List<Submission> findByContestant(String contestantId);

    List<Submission> findByProblem(String problemId);

    List<Submission> findAll();
}
