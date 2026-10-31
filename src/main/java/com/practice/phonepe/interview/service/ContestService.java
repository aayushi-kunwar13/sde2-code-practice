package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Contestant;
import com.practice.phonepe.interview.entities.Problem;
import com.practice.phonepe.interview.entities.Submission;
import com.practice.phonepe.interview.exception.AlreadySolvedException;
import com.practice.phonepe.interview.exception.EntityNotFoundException;
import com.practice.phonepe.interview.repository.ContestantRepository;
import com.practice.phonepe.interview.repository.ProblemRepository;
import com.practice.phonepe.interview.repository.SubmissionRepository;
import com.practice.phonepe.interview.strategy.RecommendationStrategy;
import com.practice.phonepe.interview.strategy.ScoringStrategy;

import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

class Pair {
    int key;
    Contestant contestant;
    public Pair(int key , Contestant contestant) {
        this.key = key;
        this.contestant = contestant;
    }
}

public class ContestService {

    private static final int RECOMMENDATION_COUNT = 5;

    private final ContestantRepository contestantRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final ScoringStrategy scoringStrategy;
    private final RecommendationStrategy recommendationStrategy;
    private final PriorityQueue<Pair> leaderBoard;

    public ContestService(ContestantRepository contestantRepository,
                           ProblemRepository problemRepository,
                           SubmissionRepository submissionRepository,
                           ScoringStrategy scoringStrategy,
                           RecommendationStrategy recommendationStrategy) {
        this.contestantRepository = contestantRepository;
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.scoringStrategy = scoringStrategy;
        this.recommendationStrategy = recommendationStrategy;
        this.leaderBoard = new PriorityQueue<>();
    }

    public SolveResult solve(String contestantId, String problemId, int timeTakenSeconds) {
        Contestant contestant = contestantRepository.findById(contestantId)
                .orElseThrow(() -> new EntityNotFoundException("Contestant not found: " + contestantId));
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("Problem not found: " + problemId));

        Set<String> solvedProblemIds = submissionRepository.findByContestant(contestantId).stream()
                .map(Submission::getProblemId)
                .collect(Collectors.toSet());
        if (solvedProblemIds.contains(problemId)) {
            throw new AlreadySolvedException(contestant.getName() + " has already solved this problem");
        }

        int scoreAwarded = scoringStrategy.calculateScore(problem, timeTakenSeconds);
        submissionRepository.save(new Submission(contestantId, problemId, timeTakenSeconds, scoreAwarded));
        //
        Pair pair = new Pair(scoreAwarded, contestant);
        leaderBoard.add(pair);

        List<Problem> candidates = problemRepository.findAll().stream()
                .filter(candidate -> !candidate.getId().equals(problemId))
                .filter(candidate -> !solvedProblemIds.contains(candidate.getId()))
                .collect(Collectors.toList());
        List<Problem> recommendations = recommendationStrategy.recommend(problem, candidates, RECOMMENDATION_COUNT);
        // M - contestant , N - problem
        // O(log(M)) + O(N)
        return new SolveResult(scoreAwarded, recommendations);
    }

    public Optional<Contestant> getLeader() {
        if (submissionRepository.findAll().isEmpty()) {
            return Optional.empty();
        }

        Map<String, Integer> totalScoreByContestant = submissionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Submission::getContestantId, Collectors.summingInt(Submission::getScoreAwarded)));

        // Assumption: ties broken by earliest-registered contestant
        Contestant leader = null;
        int bestScore = Integer.MIN_VALUE;
        for (Contestant contestant : contestantRepository.findAll()) {
            int score = totalScoreByContestant.getOrDefault(contestant.getId(), 0);
            if (score > bestScore) {
                bestScore = score;
                leader = contestant;
            }
        }
        return Optional.ofNullable(leader);
    }


}
