package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Problem;

public interface ScoringStrategy {

    int calculateScore(Problem problem, int timeTakenSeconds);
}
