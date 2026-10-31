package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Problem;

public class TimeAdjustedScoreStrategy implements ScoringStrategy {

    private final int fastSolveThresholdSeconds;

    public TimeAdjustedScoreStrategy(int fastSolveThresholdSeconds) {
        this.fastSolveThresholdSeconds = fastSolveThresholdSeconds;
    }

    @Override
    public int calculateScore(Problem problem, int timeTakenSeconds) {
        // Assumption: no exact formula was specified. Full score within the threshold, then a
        // 10% penalty per additional threshold interval, floored at half the base score.
        if (timeTakenSeconds <= fastSolveThresholdSeconds) {
            return problem.getBaseScore();
        }
        int intervalsLate = (timeTakenSeconds - fastSolveThresholdSeconds) / fastSolveThresholdSeconds;
        double penalty = Math.min(0.5, intervalsLate * 0.1);
        return (int) Math.round(problem.getBaseScore() * (1 - penalty));
    }
}
