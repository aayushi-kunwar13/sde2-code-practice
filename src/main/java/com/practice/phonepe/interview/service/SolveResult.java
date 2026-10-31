package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Problem;

import java.util.List;

public class SolveResult {

    private final int scoreAwarded;
    private final List<Problem> recommendedProblems;

    public SolveResult(int scoreAwarded, List<Problem> recommendedProblems) {
        this.scoreAwarded = scoreAwarded;
        this.recommendedProblems = recommendedProblems;
    }

    public int getScoreAwarded() {
        return scoreAwarded;
    }

    public List<Problem> getRecommendedProblems() {
        return recommendedProblems;
    }
}
