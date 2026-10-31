package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Problem;
import com.practice.phonepe.interview.entities.Submission;
import com.practice.phonepe.interview.exception.EntityNotFoundException;
import com.practice.phonepe.interview.repository.ProblemRepository;
import com.practice.phonepe.interview.repository.SubmissionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProblemService {

    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;

    public ProblemService(ProblemRepository problemRepository, SubmissionRepository submissionRepository) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
    }

    public void addProblem(Problem problem) {
        problemRepository.save(problem);
    }

    public List<Problem> fetchProblems(Predicate<Problem> filter, Comparator<Problem> sortBy) {
        return problemRepository.findAll().stream()
                .filter(filter)
                .sorted(sortBy)
                .collect(Collectors.toList());
    }

    public ProblemStats getProblemStats(String problemId) {
        requireProblem(problemId);
        List<Submission> submissions = submissionRepository.findByProblem(problemId);
        double averageTime = submissions.stream()
                .mapToInt(Submission::getTimeTakenSeconds)
                .average()
                .orElse(0);
        return new ProblemStats(submissions.size(), averageTime);
    }

    // "Liked" isn't defined anywhere in the spec beyond solving, so it's treated as solve count.
    public List<Problem> getTopNProblems(String tag, int n) {
        return problemRepository.findAll().stream()
                .filter(problem -> problem.getTags().contains(tag))
                .sorted(Comparator.comparingInt((Problem problem) -> submissionRepository.findByProblem(problem.getId()).size()).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    private Problem requireProblem(String problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("Problem not found: " + problemId));
    }
}
