package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Problem;

public class FixedScoreStrategy implements ScoringStrategy {

    @Override
    public int calculateScore(Problem problem, int timeTakenSeconds) {
        return problem.getBaseScore();
    }
}
