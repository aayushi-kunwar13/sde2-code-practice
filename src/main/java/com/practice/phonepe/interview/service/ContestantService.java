package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Contestant;
import com.practice.phonepe.interview.entities.Problem;
import com.practice.phonepe.interview.exception.EntityNotFoundException;
import com.practice.phonepe.interview.repository.ContestantRepository;
import com.practice.phonepe.interview.repository.ProblemRepository;
import com.practice.phonepe.interview.repository.SubmissionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ContestantService {

    private final ContestantRepository contestantRepository;
    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    public ContestantService(ContestantRepository contestantRepository,
                              SubmissionRepository submissionRepository,
                              ProblemRepository problemRepository) {
        this.contestantRepository = contestantRepository;
        this.submissionRepository = submissionRepository;
        this.problemRepository = problemRepository;
    }

    public Contestant addContestant(String name, String department) {
        Contestant contestant = new Contestant(name, department);
        contestantRepository.save(contestant);
        return contestant;
    }

    public List<Problem> fetchSolvedProblems(String contestantId) {
        requireContestant(contestantId);
        return submissionRepository.findByContestant(contestantId).stream()
                .map(submission -> problemRepository.findById(submission.getProblemId())
                        .orElseThrow(() -> new EntityNotFoundException("Problem not found: " + submission.getProblemId())))
                .collect(Collectors.toList());
    }

    private Contestant requireContestant(String contestantId) {
        return contestantRepository.findById(contestantId)
                .orElseThrow(() -> new EntityNotFoundException("Contestant not found: " + contestantId));
    }
}
