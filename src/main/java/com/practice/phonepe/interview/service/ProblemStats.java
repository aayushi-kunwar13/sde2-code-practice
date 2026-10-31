package com.practice.phonepe.interview.service;

public class ProblemStats {

    private final int solveCount;
    private final double averageTimeTakenSeconds;

    public ProblemStats(int solveCount, double averageTimeTakenSeconds) {
        this.solveCount = solveCount;
        this.averageTimeTakenSeconds = averageTimeTakenSeconds;
    }

    public int getSolveCount() {
        return solveCount;
    }

    public double getAverageTimeTakenSeconds() {
        return averageTimeTakenSeconds;
    }
}
